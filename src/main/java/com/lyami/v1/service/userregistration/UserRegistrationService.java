package com.lyami.v1.service.userregistration;

import org.springframework.stereotype.Service;

import com.lyami.v1.dto.request.UserProfileRegistrationRequest;
import com.lyami.v1.dto.response.UserProfileResponse;

@Service
public interface UserRegistrationService {
	
	 void registerUser(UserProfileRegistrationRequest userRegistrationRequest);
	 
	 UserProfileResponse getUserProfileDetails(String email);
	 
}
