package com.lyami.v1.controller.user;

import com.lyami.v1.constants.ApplicationConstants;
import com.lyami.v1.dto.UserDetailsImpl;
import com.lyami.v1.dto.entity.user.UserBusinessDetails;
import com.lyami.v1.dto.request.UserBusinessDetailsRegistrationRequest;
import com.lyami.v1.dto.request.UserBusinessDetailsUpdateRequest;
import com.lyami.v1.dto.response.UserProfileResponse;
import com.lyami.v1.service.user.UserBusinessDetailsService;
import com.lyami.v1.validator.ValidEmailId;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/v1/user")
@Slf4j
public class UserBusinessDetailsController {

    @Autowired
    UserBusinessDetailsService userBusinessDetailsService;

    @GetMapping("/business-details")
    @PreAuthorize(ApplicationConstants.USER_ROLE_AUTHORIZER)
    public ResponseEntity<UserProfileResponse> getUserBusinessDetails(@NotBlank @ValidEmailId @RequestParam String email){
        UserProfileResponse userProfileResponse = null;
        if(verifyUser(email)){
            log.info("email::"+ email);
            userProfileResponse = userBusinessDetailsService.getUserBusinessDetails(email);
        } else return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        return ResponseEntity.ok(userProfileResponse);
    }


    @PostMapping("/business-details/add")
    @PreAuthorize(ApplicationConstants.USER_ROLE_AUTHORIZER)
    public ResponseEntity<Void> addBusinessDetails(@Valid @RequestBody UserBusinessDetailsRegistrationRequest userBusinessDetailsRegistrationRequest) {

        if(verifyUser(userBusinessDetailsRegistrationRequest.getEmail())){
            log.info("UserBusinessDetailsRegistrationRequest::"+ userBusinessDetailsRegistrationRequest);
            userBusinessDetailsService.addBusinessDetails(userBusinessDetailsRegistrationRequest);
        } else return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/business-details/edit")
    @PreAuthorize(ApplicationConstants.USER_ROLE_AUTHORIZER)
    public ResponseEntity<Void> updateBusinessDetails(@Valid @RequestBody UserBusinessDetailsUpdateRequest userBusinessDetailsUpdateRequest) {

        if(verifyUser(userBusinessDetailsUpdateRequest.getEmail())){
            log.info("UserBusinessDetailsUpdateRequest::"+ userBusinessDetailsUpdateRequest);
            userBusinessDetailsService.updateBusinessDetails(userBusinessDetailsUpdateRequest);
        } else return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    private Boolean verifyUser(String email){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            return userDetails.getEmail().equals(email);
        }
        return false;
    }

}
