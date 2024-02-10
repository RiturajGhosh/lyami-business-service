package com.lyami.v1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.lyami.v1.dto.request.UserRegistrationRequest;
import com.lyami.v1.service.userregistration.UserRegistrationService;

import jakarta.validation.Valid;

public class UserRegistrationController {
	
	@Autowired
	UserRegistrationService userRegistrationService;
	
	@PostMapping("/userRegistration")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerUser(@Valid @RequestBody UserRegistrationRequest userRegistrationRequest) {
		userRegistrationService.registerUser(userRegistrationRequest);
    }
}
