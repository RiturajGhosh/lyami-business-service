/*
 * This code is proprietary and confidential to Lyamii.com.
 * All rights are reserved. Unauthorized use, reproduction,
 * or distribution of this code is strictly prohibited.
 */
package com.lyami.v1.authentication.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lyami.v1.authentication.dto.UserDetailsImpl;
import com.lyami.v1.authentication.dto.entity.ERole;
import com.lyami.v1.authentication.dto.entity.Role;
import com.lyami.v1.authentication.dto.entity.User;
import com.lyami.v1.authentication.dto.request.JwtRefreshRequest;
import com.lyami.v1.authentication.dto.request.LoginRequest;
import com.lyami.v1.authentication.dto.request.SignupRequest;
import com.lyami.v1.authentication.dto.response.JwtResponse;
import com.lyami.v1.authentication.repository.RoleRepository;
import com.lyami.v1.authentication.repository.UserRepository;
import com.lyami.v1.authentication.util.JwtUtils;
import com.lyami.v1.common.exception.LyamiBusinessException;
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

import static com.lyami.v1.common.constants.ApplicationConstants.OTP_EXPIRY_DURATION_MS;

@Slf4j
@Service
public class AuthenticationService {

    private UserRepository userRepository;

    private RoleRepository roleRepository;

    private PasswordEncoder encoder;

    private AuthenticationManager authenticationManager;

    private UserDetailsService userDetailsService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private JwtUtils jwtUtils;

    @Value("${signup.invalid.username.alreadytaken}")
    private String invalidUserName;

    @Value("${signup.invalid.email.alreadytaken}")
    private String invalidEmail;

    @Autowired
    public AuthenticationService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder encoder,
                                 AuthenticationManager authenticationManager, JwtUtils jwtUtils, UserDetailsService userDetailsService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.userDetailsService = userDetailsService;
    }

    public ResponseEntity<String> registerUserService(SignupRequest signUpRequest) throws LyamiBusinessException {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            throw new LyamiBusinessException(invalidUserName);
        } else if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new LyamiBusinessException(invalidEmail);
        } else {
            // Create new user's account
            User user = new User(signUpRequest.getUsername(), signUpRequest.getEmail(),
                    encoder.encode(signUpRequest.getPassword()));
            Set<Role> roles = new HashSet<>();
            if (CollectionUtils.isEmpty(signUpRequest.getRole())) {
                addFindRole(ERole.ROLE_USER, roles);
            } else {
                addUserRoleBasedOnInputReq(signUpRequest.getRole(), roles);
            }
            user.setRoles(roles);
            userRepository.save(user);
            return ResponseEntity.ok("User registered successfully!");
        }
    }

    public ResponseEntity<JwtResponse> authenticateUser(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles));
    }

    public ResponseEntity<JwtResponse> refreshJwtToekn(JwtRefreshRequest jwtRequest) {
        //need to implement try block here or in global exception handling need to add few more
        //handlers. as with this impl if username is wrong we are getting 500; but there is no point in doing a db call here
        //if it is failure it is unauthorized.--- that's the thought process
        if (jwtRequest.getToken() != null && jwtUtils.validateJwtToken(jwtRequest.getToken())) {
            final var userDetails = userDetailsService.loadUserByUsername(jwtRequest.getUserName());
            final String token = jwtUtils.generateJwtToken(userDetails);
            return ResponseEntity.ok(new JwtResponse(userDetails.getUsername(), token));
        }
        //will remove the below line and need to implement one Unauth runtime custom exception and throw it from here
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
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

    @SneakyThrows
    public void verifyEmail(String emailId) {
        /* if already signed up return some error message saying account already exists*/
        if (isUserEmailSignedUp(emailId)) {
            throw new LyamiBusinessException(invalidEmail);
        }
        //if all ok, generate a random 6 digit otp, store the encoded otp along with the email id with some expiry time, isSignedUp=false, isOtpVerified=false
        String encodedOtp = encoder.encode(generateSixDigitOtp());
        var user = mapUserDto(emailId, System.currentTimeMillis() + OTP_EXPIRY_DURATION_MS,
                false, false, encodedOtp);
        userRepository.save(user);
        //put a message on kafka email-verify topic with the OTP and emailId
        String payload = objectMapper.writeValueAsString(user);
        //yet to implement kafka producer
        //kafkaUtil.sendMessage(payload);
        //give 204 no content after all the above steps
    }

    private User mapUserDto(String emailId, long time, boolean isSignedUp, boolean isOtpVerified, String otp) {
        User user = new User();
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

    /**
     * check if the email id already exists and isSignedUp = true.
     *
     * @param emailId
     * @return true if email id is signed up user email
     */
    public boolean isUserEmailSignedUp(String emailId) {
        var userDtoOptional = userRepository.findByEmail(emailId);
        if (userDtoOptional.isPresent()) {
            var user = userDtoOptional.get();
            return BooleanUtils.isTrue(user.getIsSignedUp());
        }
        return false;
    }
}
