package com.lyami.v1.service.auth;

import com.lyami.v1.dto.request.JwtRefreshRequest;
import com.lyami.v1.dto.request.LoginRequest;
import com.lyami.v1.dto.request.OTPVerificationRequest;
import com.lyami.v1.dto.request.SignupRequest;
import com.lyami.v1.dto.response.JwtResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface AuthenticationService {

    void registerUser(SignupRequest signupRequest);

    ResponseEntity<JwtResponse> authenticateUser(LoginRequest loginRequest);

    ResponseEntity<JwtResponse> refreshJwtToekn(JwtRefreshRequest jwtRequest);

    void verifyEmail(String emailId);

    void verifyOtp(OTPVerificationRequest otpVerificationRequest);

}
