/*
package com.lyami.v1.service.stayregistration;

import com.lyami.v1.dto.request.StayRegistrationImageRequest;
import com.lyami.v1.mapper.StayRegistrationMapper;
import com.lyami.v1.repository.StayRegImageRepository;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class StayServiceImplTest {

    @InjectMocks
    private StayServiceImpl stayService;

    @Mock
    private StayRegImageRepository stayRegImageRepository;

    @Spy
    private StayRegistrationMapper stayRegistrationMapper = Mappers.getMapper(StayRegistrationMapper.class);

    @Test
    @SneakyThrows
    void uploadImagesTest() {
        String registrationId = "1";
        var stayRegImageReqList = getStayRegistrationImageRequests();
        // Mock the behavior of stayRegImageRepository.saveAllAndFlush to throw an exception
        when(stayRegImageRepository.saveAllAndFlush(anyList())).thenReturn(null);
        stayService.uploadImages(registrationId, stayRegImageReqList);
    }


    private ArrayList<StayRegistrationImageRequest> getStayRegistrationImageRequests() throws IOException {
        var file1 = new MockMultipartFile("file1", "file1.jpg", "image/jpeg", "file1-content".getBytes());
        var file2 = new MockMultipartFile("file2", "file2.jpg", "image/jpeg", "file1-content".getBytes());
        var stayRegImageReqList = new ArrayList<StayRegistrationImageRequest>();
        var stayRegImageReq1 = new StayRegistrationImageRequest(file1.getBytes().toString(), StayRegistrationImageRequest.ImageRegType.DORM);
        var stayRegImageReq2 = new StayRegistrationImageRequest(file2, StayRegistrationImageRequest.ImageRegType.BUDGET);
        stayRegImageReqList.add(stayRegImageReq1);
        stayRegImageReqList.add(stayRegImageReq2);
        return stayRegImageReqList;
    }
}*/
