package com.lyami.v1.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StayRegistrationImageRequest {
    @NotNull
    private String file;
    @NotNull
    private String fileName;
    @NotNull
    private String fileType;
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
