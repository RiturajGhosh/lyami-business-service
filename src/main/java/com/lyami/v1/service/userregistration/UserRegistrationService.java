package com.lyami.v1.service.userregistration;

import org.springframework.stereotype.Service;

import com.lyami.v1.dto.request.UserRegistrationRequest;

@Service
public interface UserRegistrationService {
	
	 void registerUser(UserRegistrationRequest userRegistrationRequest);
	 
}
