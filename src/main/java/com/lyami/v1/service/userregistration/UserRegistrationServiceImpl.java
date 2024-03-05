package com.lyami.v1.service.userregistration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lyami.v1.dto.request.UserProfileRegistrationRequest;
import com.lyami.v1.dto.response.UserProfileResponse;
import com.lyami.v1.exception.LyamiBusinessException;
import com.lyami.v1.mapper.UserRegistrationMapper;
import com.lyami.v1.repository.UserBusinessDetailsRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserRegistrationServiceImpl implements UserRegistrationService{

	private UserBusinessDetailsRepository userRegisterRepository;
	private UserRegistrationMapper userRegistrationMapper;
	
	@Autowired
	public UserRegistrationServiceImpl(UserBusinessDetailsRepository userRegisterRepository,
			UserRegistrationMapper userRegistrationMapper) {
		super();
		this.userRegisterRepository = userRegisterRepository;
		this.userRegistrationMapper = userRegistrationMapper;
	}


	/*
	 * Purpose of this method to register new user that are coming on Lyamii portal.
	 *  
	 */
	@Override
	public void registerUser(UserProfileRegistrationRequest userRegistrationRequest) {
		// TODO Auto-generated method stub
		userRegisterRepository.findByEmail(
				userRegistrationRequest.getEmail()).ifPresent(s->{throw new LyamiBusinessException("User already exists");});
		var userBusinessDetails = userRegistrationMapper.mapUserProfileRegistrationReqtoEntity(userRegistrationRequest);
		log.info("registerUserDetails Country::"+userBusinessDetails.getCountry().getCountryName());
		userRegisterRepository.save(userBusinessDetails);
	}


	@Override
	public UserProfileResponse getUserProfileDetails(String email) {
		// TODO Auto-generated method stub
		var userBusinessDetails = userRegisterRepository.findByEmail(
				email).orElseThrow(()->
				new LyamiBusinessException("User does not exists"));
		log.info("userBusinessDetails::"+userBusinessDetails);
		var userProfileResponse = userRegistrationMapper.mapUserBusinessDeatailstoUserProfileResponse(userBusinessDetails);
		return userProfileResponse;
	}

}
