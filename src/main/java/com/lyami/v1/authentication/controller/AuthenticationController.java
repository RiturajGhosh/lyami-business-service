/*
 * This code is proprietary and confidential to Lyamii.com.
 * All rights are reserved. Unauthorized use, reproduction,
 * or distribution of this code is strictly prohibited.
 */
package com.lyami.v1.authentication.controller;

import com.lyami.v1.authentication.dto.request.JwtRefreshRequest;
import com.lyami.v1.authentication.dto.request.LoginRequest;
import com.lyami.v1.authentication.dto.request.OTPVerificationRequest;
import com.lyami.v1.authentication.dto.request.SignupRequest;
import com.lyami.v1.authentication.service.AuthenticationService;
import com.lyami.v1.common.validator.ValidEmailId;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

@RestController
@RequestMapping("/v1/authenticate")
@Slf4j
public class AuthenticationController {

    private AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        return authenticationService.registerUserService(signUpRequest);
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        return authenticationService.authenticateUser(loginRequest);
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody JwtRefreshRequest jwtRequest) {
        return authenticationService.refreshJwtToekn(jwtRequest);
    }

    @PostMapping("/verify/email")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void verifyEmail(@RequestParam @NotBlank @ValidEmailId String emailId){
        authenticationService.verifyEmail(emailId);
    }

    @PostMapping("verify/otp")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void verifyOtp(@Valid OTPVerificationRequest otpVerificationRequest){
        authenticationService.verifyOtp(otpVerificationRequest);
    }

   /* after otp verification is successful user will enter username, password,
    from client side a req with email, password, username will come to server side,
        1. fetch the record with the email id
        2. If email id not present show no record found
        3. else check if isSignedUp = true -> if yes then return account already exist
        4. check if isOtpVerified = false -> if yes then return OTP verification required
        5. save the user in db with isSignedUp= true, encoded password, username. role
    */


}
