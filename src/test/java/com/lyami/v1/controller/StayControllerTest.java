/*
package com.lyami.v1.controller;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import com.lyami.v1.dto.request.StayRegistrationImageRequest;
import com.lyami.v1.service.auth.UserDetailsServiceImpl;
import com.lyami.v1.service.stayregistration.StayService;
import com.lyami.v1.util.JwtUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.beans.Visibility;
import java.util.Arrays;
import java.util.List;

@ExtendWith(SpringExtension.class)
@WebMvcTest(StayController.class)
class StayControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @InjectMocks
    private StayController stayController;

    @MockBean
    private StayService stayService;

    @MockBean
    private JwtUtils jwtUtils;

    @MockBean
    private UserDetailsServiceImpl userDetailsService;


    @Test
    void uploadStayRegistrationImagesTest() throws Exception {
        String registrationId = "123";

        // Create a list of StayRegistrationImageRequest objects
        StayRegistrationImageRequest.ImageRegType imageType1 = StayRegistrationImageRequest.ImageRegType.PROPERTY;
        StayRegistrationImageRequest.ImageRegType imageType2 = StayRegistrationImageRequest.ImageRegType.DORM;

        StayRegistrationImageRequest imageRequest1 = new StayRegistrationImageRequest(
                new MockMultipartFile("file1", "image1.jpg", "image/jpeg", "image data 1".getBytes()),
                imageType1
        );

        StayRegistrationImageRequest imageRequest2 = new StayRegistrationImageRequest(
                new MockMultipartFile("file2", "image2.jpg", "image/jpeg", "image data 2".getBytes()),
                imageType2
        );

        List<StayRegistrationImageRequest> requestList = Arrays.asList(imageRequest1, imageRequest2);

        // Convert the list to JSON
       // objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

        String jsonRequest = objectMapper.writeValueAsString(requestList);

        mockMvc.perform(MockMvcRequestBuilders.post("/uploadimage/{registrationId}", registrationId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}
*/
