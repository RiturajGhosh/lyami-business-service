package com.lyami.v1.dto.request;

import com.lyami.v1.validator.ValidEmailId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OTPVerificationRequest {
    @ValidEmailId
    private String emailId;
    private String otp;
}
