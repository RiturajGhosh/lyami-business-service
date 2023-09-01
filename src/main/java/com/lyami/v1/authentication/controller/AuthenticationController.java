/*
 * This code is proprietary and confidential to Lyamii.com.
 * All rights are reserved. Unauthorized use, reproduction,
 * or distribution of this code is strictly prohibited.
 */
package com.lyami.v1.authentication.controller;

import com.lyami.v1.authentication.dto.UserDetailsImpl;
import com.lyami.v1.authentication.dto.entity.ERole;
import com.lyami.v1.authentication.dto.entity.Role;
import com.lyami.v1.authentication.dto.entity.User;
import com.lyami.v1.authentication.dto.request.LoginRequest;
import com.lyami.v1.authentication.dto.request.SignupRequest;
import com.lyami.v1.authentication.dto.response.JwtResponse;
import com.lyami.v1.authentication.repository.RoleRepository;
import com.lyami.v1.authentication.repository.UserRepository;
import com.lyami.v1.authentication.service.AuthenticationService;
import com.lyami.v1.authentication.util.JwtUtils;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

}
