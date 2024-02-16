package com.lyami.v1.service.userregistration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lyami.v1.dto.request.UserRegistrationRequest;
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
	public void registerUser(UserRegistrationRequest userRegistrationRequest) {
		// TODO Auto-generated method stub
		var userBusinessDetails = userRegisterRepository.findByEmail(
				userRegistrationRequest.getEmail()).orElseThrow(()->
				new LyamiBusinessException("User already exits"));
		log.info("userBusinessDetails::"+userBusinessDetails);
		userBusinessDetails = userRegistrationMapper.mapUserRegistrationReqtoEntity(userRegistrationRequest);
		userRegisterRepository.save(userBusinessDetails);
	}

}
