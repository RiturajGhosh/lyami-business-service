package com.lyami.v1.controller;

import com.lyami.v1.constants.ApplicationConstants;
import com.lyami.v1.dto.request.StayRegistrationImageRequest;
import com.lyami.v1.dto.request.StayRegistrationRequest;
import com.lyami.v1.service.stayregistration.StayService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/stay")
public class StayController {

    private StayService stayService;

    @Autowired
    public StayController(StayService stayService) {
        this.stayService = stayService;
    }

    @PostMapping("/registration")
    @PreAuthorize(ApplicationConstants.STAY_USER_ROLE_AUTHORIZER)
    public ResponseEntity<Void> registerStays(@Validated({StayRegistrationRequest.StayRegistrationRequestFieldValidatorGroup.class,
            StayRegistrationRequest.StayRegistrationRequestClassValidatorGroup.class})
                                              @RequestBody StayRegistrationRequest stayRegistrationRequest) {
        var id = stayService.registerStay(stayRegistrationRequest);
        HttpHeaders headers = new HttpHeaders();
        headers.add("id", Integer.toString(id));
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @PostMapping("/uploadimage/{registrationId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize(ApplicationConstants.STAY_USER_ROLE_AUTHORIZER)
    public void uploadStayRegistrationImages(@PathVariable("registrationId") String registrationId,
                                             @RequestBody @NotNull List<StayRegistrationImageRequest> files) throws IOException {
        stayService.uploadImages(registrationId, files);
    }
}
