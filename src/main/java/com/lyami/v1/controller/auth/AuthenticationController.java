/*
 * This code is proprietary and confidential to Lyamii.com.
 * All rights are reserved. Unauthorized use, reproduction,
 * or distribution of this code is strictly prohibited.
 */
package com.lyami.v1.controller.auth;

import com.lyami.v1.dto.request.JwtRefreshRequest;
import com.lyami.v1.dto.request.LoginRequest;
import com.lyami.v1.dto.request.OTPVerificationRequest;
import com.lyami.v1.dto.request.SignupRequest;
import com.lyami.v1.dto.response.JwtResponse;
import com.lyami.v1.service.auth.AuthenticationService;
import com.lyami.v1.validator.ValidEmailId;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/v1/authenticate")
@Slf4j
public class AuthenticationController {

    private AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        authenticationService.registerUser(signUpRequest);
    }

    @PostMapping("/signin")
    public ResponseEntity<JwtResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        return authenticationService.authenticateUser(loginRequest);
    }

    @PostMapping("/refresh")
    public ResponseEntity<JwtResponse> refreshToken(@RequestBody JwtRefreshRequest jwtRequest) {
        return authenticationService.refreshJwtToekn(jwtRequest);
    }

    @PostMapping("/verify/email")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void verifyEmail(@RequestParam @NotBlank @ValidEmailId String emailId) {
        authenticationService.verifyEmail(emailId);
    }

    @PostMapping("/verify/otp")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void verifyOtp(@Valid @RequestBody OTPVerificationRequest otpVerificationRequest) {
        authenticationService.verifyOtp(otpVerificationRequest);
    }
}
