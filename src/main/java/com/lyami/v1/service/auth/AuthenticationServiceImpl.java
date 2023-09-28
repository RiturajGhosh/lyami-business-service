/*
 * This code is proprietary and confidential to Lyamii.com.
 * All rights are reserved. Unauthorized use, reproduction,
 * or distribution of this code is strictly prohibited.
 */
package com.lyami.v1.service.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lyami.v1.dto.UserDetailsImpl;
import com.lyami.v1.dto.entity.ERole;
import com.lyami.v1.dto.entity.Role;
import com.lyami.v1.dto.entity.User;
import com.lyami.v1.dto.request.JwtRefreshRequest;
import com.lyami.v1.dto.request.LoginRequest;
import com.lyami.v1.dto.request.OTPVerificationRequest;
import com.lyami.v1.dto.request.SignupRequest;
import com.lyami.v1.dto.response.JwtResponse;
import com.lyami.v1.repository.RoleRepository;
import com.lyami.v1.repository.UserRepository;
import com.lyami.v1.service.mail.MailingService;
import com.lyami.v1.util.JwtUtils;
import com.lyami.v1.event.KafkaProducerService;
import com.lyami.v1.exception.LyamiBusinessException;
import com.lyami.v1.constants.ApplicationConstants;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.lyami.v1.constants.ApplicationConstants.OTP_MAIL_HTML;
import static com.lyami.v1.constants.ApplicationConstants.OTP_MAIL_SUBJECT;

@Slf4j
@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private UserRepository userRepository;

    private RoleRepository roleRepository;

    private PasswordEncoder encoder;

    private AuthenticationManager authenticationManager;

    private UserDetailsService userDetailsService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private JwtUtils jwtUtils;

    private KafkaProducerService kafkaProducerService;

    private MailingService mailingService;

    @Value("${signup.invalid.username.alreadytaken}")
    private String invalidUserName;

    @Value("${signup.invalid.email.alreadytaken}")
    private String invalidEmail;

    @Value("${kafka.topic.name.emailverification}")
    private String emailVerificationTopic;

    @Autowired
    public AuthenticationServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder encoder,
                                     AuthenticationManager authenticationManager, JwtUtils jwtUtils,
                                     UserDetailsService userDetailsService, KafkaProducerService kafkaProducerService,
                                     MailingService mailingService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.userDetailsService = userDetailsService;
        this.kafkaProducerService = kafkaProducerService;
        this.mailingService = mailingService;
    }

    @Override
    public ResponseEntity<JwtResponse> authenticateUser(LoginRequest loginRequest) {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles));
    }

    @Override
    public ResponseEntity<JwtResponse> refreshJwtToekn(JwtRefreshRequest jwtRequest) {
        //need to implement try block here or in global exception handling need to add few more
        //handlers. as with this impl if username is wrong we are getting 500; but there is no point in doing a db call here
        //if it is failure it is unauthorized.--- that's the thought process
        if (jwtRequest.getToken() != null && jwtUtils.validateJwtToken(jwtRequest.getToken())) {
            final var userDetails = userDetailsService.loadUserByUsername(jwtRequest.getEmail());
            final String token = jwtUtils.generateJwtToken(userDetails);
            final String email = ((UserDetailsImpl) userDetails).getEmail();
            return ResponseEntity.ok(new JwtResponse(email, token));
        }
        //will remove the below line and need to implement one Unauth runtime custom exception and throw it from here
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    /**
     * Generates a 6 digit OTP and publishes the event to kafka,
     * if user is not already signed up
     *
     * @param emailId
     */
    @Override
    @SneakyThrows
    public void verifyEmail(String emailId) {
        /* if already signed up return some error message saying account already exists*/
        var user = validateAndGetNotSignedUpUser(emailId);
        //if all ok, generate a random 6 digit otp, store the encoded otp along with the email id with some expiry time, isSignedUp=false, isOtpVerified=false
        String generatedOTP = generateSixDigitOtp();
        String encodedOtp = encoder.encode(generatedOTP);
        user = mapUserDto(user, emailId, System.currentTimeMillis() + ApplicationConstants.OTP_EXPIRY_DURATION_MS,
                false, false, encodedOtp);
        userRepository.save(user);
        //put a message on kafka email-verify topic with the OTP and emailId
        String payload = objectMapper.writeValueAsString(user);
        //yet to implement kafka producer - We are thinking to downgrade the perfx as we don't have customer base and thus we can optimize cloud cost
        //we want to decomission the kafka service, and implement all things synchronously, we are expecting max 0.20 TPS
       // kafkaProducerService.sendMessage(emailVerificationTopic, payload);
        String mailContent = OTP_MAIL_HTML.replace("{{otp}}", generatedOTP);
        mailingService.sendEmail(emailId, "infinity.ocean000@gmail.com", OTP_MAIL_SUBJECT, mailContent);
    }

    /**
     * Verify if the entered otp
     *
     * @param otpVerificationRequest
     */
    @Override
    @SneakyThrows
    public void verifyOtp(OTPVerificationRequest otpVerificationRequest) {
        //fetch the encoded otp and expiry time based on the email id
        //if there is no record with the email id send some error message. no records found
        var user = userRepository.findByEmail(otpVerificationRequest.getEmailId())
                .orElseThrow(() -> new LyamiBusinessException(invalidEmail));
        //if there is no otp expirytime in db throw exception
        if (user.getOtpExpiryTime() == null) {
            throw new LyamiBusinessException();
        }
        //else decode the otp, check if the expiry time is not over, match with the otp sent by the user
        if (user.getOtpExpiryTime() < System.currentTimeMillis()) {
            //if not matched, send wrong otp error message
            if (!encoder.matches(otpVerificationRequest.getOtp(), user.getEmailVerificationOtp())) {
                throw new LyamiBusinessException();
            }
        }
        //update isOtpVerified in db to true.
        user.setIsOtpVerified(true);
        userRepository.save(user);
    }

    /**
     * check if the email id already exists and isSignedUp = true.
     *
     * @param emailId
     * @return user if user is not signed up
     */
    private User validateAndGetNotSignedUpUser(String emailId) {
        User user = new User();
        var userDtoOptional = userRepository.findByEmail(emailId);
        if (userDtoOptional.isPresent()) {
            user = userDtoOptional.get();
            if (BooleanUtils.isTrue(user.getIsSignedUp()))
                throw new LyamiBusinessException(invalidEmail);
        }
        return user;
    }

    /**
     * Method for user sign up with Lyami
     * <p>
     * After otp verification is successful user will enter username, password,
     * from client side a req with email, password, username will come to server side,
     * 1. fetch the record with the email id
     * 2. If email id not present show no record found -> otp verification is imcomplete
     * 3. else check if isSignedUp = true -> if yes then return account already exist
     * 4. check if isOtpVerified = false -> if yes then return OTP verification required
     * 5. save the user in db with isSignedUp= true, encoded password, username. role
     *
     * @param signupRequest
     */
    @Override
    @SneakyThrows
    public void registerUser(SignupRequest signupRequest) {
        User user = userRepository.findByEmail(signupRequest.getEmail())
                .orElseThrow(() -> new LyamiBusinessException());
        if (BooleanUtils.isTrue(user.getIsSignedUp())) {
            throw new LyamiBusinessException();
        } else if (BooleanUtils.isNotTrue(user.getIsOtpVerified())) {
            throw new LyamiBusinessException();
        }
        user = mapUserNamePasswordRole(user, signupRequest);
        user.setIsSignedUp(true);
        userRepository.save(user);
    }

    private User mapUserNamePasswordRole(User user, SignupRequest signupRequest) {
        user.setUsername(signupRequest.getUsername());
        user.setPassword(encoder.encode(signupRequest.getPassword()));
        Set<Role> roles = new HashSet<>();
        if (CollectionUtils.isEmpty(signupRequest.getRole())) {
            addFindRole(ERole.ROLE_USER, roles);
        } else {
            addUserRoleBasedOnInputReq(signupRequest.getRole(), roles);
        }
        user.setRoles(roles);
        return user;
    }

    private void addUserRoleBasedOnInputReq(Set<String> inputRoles, Set<Role> roles) {
        inputRoles.forEach(role -> {
            switch (role) {
                case "admin" -> addFindRole(ERole.ROLE_ADMIN, roles);
                case "mod" -> addFindRole(ERole.ROLE_MODERATOR, roles);
                default -> addFindRole(ERole.ROLE_USER, roles);
            }
        });
    }

    private void addFindRole(ERole roleUser, Set<Role> roles) {
        Role userRole = roleRepository.findByName(roleUser).orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        roles.add(userRole);
    }

    private User mapUserDto(User user, String emailId, long time, boolean isSignedUp, boolean isOtpVerified, String otp) {
        user.setEmail(emailId);
        user.setIsSignedUp(isSignedUp);
        user.setIsOtpVerified(isOtpVerified);
        user.setOtpExpiryTime(time);
        user.setEmailVerificationOtp(otp);
        return user;
    }

    private String generateSixDigitOtp() {
        SecureRandom secureRandom = new SecureRandom();
        return String.format("%06d", secureRandom.nextInt(1000000));
    }


}
