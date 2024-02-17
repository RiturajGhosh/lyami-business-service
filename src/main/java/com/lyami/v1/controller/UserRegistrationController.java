package com.lyami.v1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.lyami.v1.controller.auth.AuthenticationController;
import com.lyami.v1.dto.request.UserRegistrationRequest;
import com.lyami.v1.service.userregistration.UserRegistrationService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/user")
public class UserRegistrationController {
	
	@Autowired
	UserRegistrationService userRegistrationService;
	
	@PostMapping("/registration")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerUser(@Valid @RequestBody UserRegistrationRequest userRegistrationRequest) {
		log.info("UserRegistrationRequest::"+userRegistrationRequest);
		userRegistrationService.registerUser(userRegistrationRequest);
    }
	
	@GetMapping("/getuser")
	public void getUserDetails() {
		log.info("UserRegistrationRequest::");
	}
}
