package com.lyami.v1.service.user;

import com.lyami.v1.dto.entity.user.UserBusinessDetails;
import com.lyami.v1.dto.request.UserBusinessDetailsRegistrationRequest;
import com.lyami.v1.dto.request.UserBusinessDetailsUpdateRequest;
import com.lyami.v1.dto.response.UserProfileResponse;
import com.lyami.v1.exception.LyamiBusinessException;
import com.lyami.v1.mapper.UserBusinessDetailsMapper;
import com.lyami.v1.repository.UserBusinessDetailsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserBusinessDetailsServiceImpl implements UserBusinessDetailsService {

    private UserBusinessDetailsRepository userBusinessDetailsRepository;
    private UserBusinessDetailsMapper userBusinessDetailsMapper;


    @Autowired
    public UserBusinessDetailsServiceImpl(UserBusinessDetailsRepository userBusinessDetailsRepository,
                                          UserBusinessDetailsMapper userBusinessDetailsMapper) {
        super();
        this.userBusinessDetailsRepository = userBusinessDetailsRepository;
        this.userBusinessDetailsMapper = userBusinessDetailsMapper;
    }

    @Override
    public UserProfileResponse getUserBusinessDetails(String email){
        UserBusinessDetails userBusinessDetails = userBusinessDetailsRepository.findByEmail(email);
        if(userBusinessDetails == null) throw new LyamiBusinessException("User does not exist");

        return userBusinessDetailsMapper.mapUserBusinessDeatailstoUserProfileResponse(userBusinessDetails);
    }
    @Override
    public void addBusinessDetails(UserBusinessDetailsRegistrationRequest userBusinessDetailsRegistrationRequest){
        UserBusinessDetails ubd = userBusinessDetailsRepository.findByEmail(userBusinessDetailsRegistrationRequest.getEmail());

        if(ubd != null) throw new LyamiBusinessException("User already exists");

        var userBusinessDetails = userBusinessDetailsMapper.mapUserProfileRegistrationReqtoEntity(userBusinessDetailsRegistrationRequest);
        userBusinessDetailsRepository.save(userBusinessDetails);
    }

    @Override
    public void updateBusinessDetails(UserBusinessDetailsUpdateRequest userBusinessDetailsUpdateRequest){
        UserBusinessDetails ubd = userBusinessDetailsRepository.findByEmail(userBusinessDetailsUpdateRequest.getEmail());

        if(ubd == null) throw new LyamiBusinessException("User does not exist");

        var userBusinessDetails = userBusinessDetailsMapper.mapUserProfileUpdateReqtoEntity(userBusinessDetailsUpdateRequest);

        userBusinessDetailsRepository.updateUserBusinessDetailsByEmail(userBusinessDetails);
    }

}
