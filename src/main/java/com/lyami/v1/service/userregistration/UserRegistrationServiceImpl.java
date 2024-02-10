package com.lyami.v1.service.userregistration;

import org.springframework.beans.factory.annotation.Autowired;

import com.lyami.v1.dto.request.UserRegistrationRequest;
import com.lyami.v1.exception.LyamiBusinessException;
import com.lyami.v1.mapper.UserRegistrationMapper;
import com.lyami.v1.repository.UserRegisterRepository;

import lombok.SneakyThrows;

public class UserRegistrationServiceImpl implements UserRegistrationService{

	private UserRegisterRepository userRegisterRepository;
	private UserRegistrationMapper userRegistrationMapper;
	
	@Autowired
	public UserRegistrationServiceImpl(UserRegisterRepository userRegisterRepository,
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
	@SneakyThrows
	public void registerUser(UserRegistrationRequest userRegistrationRequest) {
		// TODO Auto-generated method stub
		var userBusinessDetails = userRegisterRepository.findByEmail(
				userRegistrationRequest.getEmail()).orElseThrow(()->new LyamiBusinessException());
		userBusinessDetails = userRegistrationMapper.mapUserRegistrationReqtoEntity(userRegistrationRequest);
		userRegisterRepository.save(userBusinessDetails);
	}

}
