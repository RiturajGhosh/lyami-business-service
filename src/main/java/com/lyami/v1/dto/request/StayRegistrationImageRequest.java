package com.lyami.v1.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Data
public class StayRegistrationImageRequest {
    @NotNull
    private MultipartFile file;
    @NotNull
    private ImageRegType type;

    @Getter
    public enum ImageRegType{
        PROPERTY(1), DORM(2), SINGLE_ROOM(3), PRIME(4), MID_RANGE(5), BUDGET(6);
        private int code;

        ImageRegType(int code) {
            this.code = code;
        }
    }
}
