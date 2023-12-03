package com.lyami.v1.service.hostel;

import com.lyami.v1.dto.entity.StayRegImage;
import com.lyami.v1.dto.request.StayRegistrationRequest;
import com.lyami.v1.mapper.StayRegistrationMapper;
import com.lyami.v1.repository.StayRegAmenityRepository;
import com.lyami.v1.repository.StayRegImageRepository;
import com.lyami.v1.repository.StayRegisterRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class StayServiceImpl implements StayService {

    private StayRegisterRepository stayRegisterRepository;
    private StayRegAmenityRepository stayRegAmenityRepository;
    private StayRegImageRepository stayRegImageRepository;
    private StayRegistrationMapper stayRegistrationMapper;

    @Autowired
    public StayServiceImpl(StayRegisterRepository stayRegisterRepository,
                           StayRegAmenityRepository stayRegAmenityRepository,
                           StayRegImageRepository stayRegImageRepository,
                           StayRegistrationMapper stayRegistrationMapper) {
        this.stayRegisterRepository = stayRegisterRepository;
        this.stayRegAmenityRepository = stayRegAmenityRepository;
        this.stayRegImageRepository = stayRegImageRepository;
        this.stayRegistrationMapper = stayRegistrationMapper;
    }

    @Override
    public Integer registerStay(StayRegistrationRequest stayRegistrationRequest) {
        var stayRegistrationEntity = stayRegistrationMapper.mapStayRegistrationReqToEntity(stayRegistrationRequest);
        var stayRegistration = stayRegisterRepository.save(stayRegistrationEntity);
        stayRegistration.getStayRegAmenities().forEach(stayRegAmenitie -> {
            stayRegAmenitie.setStayRegistrationId(stayRegistration.getId());
            stayRegAmenityRepository.save(stayRegAmenitie);
        });
        log.info("entity is: {}", stayRegistration);
        return stayRegistration.getId();
    }

    @Override
    public void uploadImages(String registrationId, List<MultipartFile> multipartFileList) {
        var imageFileEntities = multipartFileList.stream().map(file -> {
                    var stayRegImage = new StayRegImage();
                    stayRegImage.setStayRegistrationId(Integer.parseInt(registrationId));
                    stayRegImage.setFileType(file.getContentType());
                    stayRegImage.setFileName(file.getOriginalFilename());
                    try {
                        stayRegImage.setImageData(file.getBytes());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    return stayRegImage;
                })
                .collect(Collectors.toList());
        stayRegImageRepository.saveAllAndFlush(imageFileEntities);
    }
}
