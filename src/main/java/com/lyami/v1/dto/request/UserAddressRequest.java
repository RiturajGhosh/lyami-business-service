package com.lyami.v1.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserAddressRequest {
  @NotBlank
  private String houseNumber;
  
  @NotBlank
  private String street;
  
  @NotBlank
  private String city;
  
  @NotBlank
  private String state;
  
  @NotBlank
  private String pincode;
}
