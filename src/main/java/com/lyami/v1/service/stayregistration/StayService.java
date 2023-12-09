package com.lyami.v1.service.stayregistration;

import com.lyami.v1.dto.request.StayRegistrationRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface StayService {
    Integer registerStay(StayRegistrationRequest stayRegistrationRequest);

    void uploadImages(String registrationId, List<MultipartFile> multipartFileList);
}
