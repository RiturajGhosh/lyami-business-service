package com.lyami.v1.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserProfileAddressRequest {
  
  @NotBlank(message = "{userregistration.address.housenumber.required}")
  private String houseNumber;
  
  @NotBlank(message = "{userregistration.address.street.required}")
  private String street;
  
  @NotBlank(message = "{userregistration.address.city.required}")
  private String city;
  
  @NotBlank(message = "{userregistration.address.state.required}")
  private String state;
  
  @NotBlank(message = "{userregistration.address.pincode.required}")
  private String pincode;
  
  @NotBlank(message = "{userregistration.address.postoffice.required}")
  private String postOffice;
  
  @NotBlank(message = "{userregistration.address.policestation.required}")
  private String policeStation;
}
