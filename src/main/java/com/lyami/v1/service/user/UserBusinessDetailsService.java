package com.lyami.v1.service.user;

import com.lyami.v1.dto.entity.user.UserBusinessDetails;
import com.lyami.v1.dto.request.UserBusinessDetailsRegistrationRequest;
import com.lyami.v1.dto.request.UserBusinessDetailsUpdateRequest;
import com.lyami.v1.dto.response.UserProfileResponse;
import org.springframework.stereotype.Service;

@Service
public interface UserBusinessDetailsService {

    void addBusinessDetails(UserBusinessDetailsRegistrationRequest userBusinessDetailsRegistrationRequest);

    void updateBusinessDetails(UserBusinessDetailsUpdateRequest userBusinessDetailsUpdateRequest);

    UserProfileResponse getUserBusinessDetails(String email);
}
