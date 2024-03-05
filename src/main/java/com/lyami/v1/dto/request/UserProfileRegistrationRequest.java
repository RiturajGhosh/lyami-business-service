package com.lyami.v1.dto.request;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.lyami.v1.validator.ValidGender;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileRegistrationRequest {
  
  @Valid	
  @NotNull (message = "{userregistration.address.required}")	
  private UserProfileAddressRequest address;
  
  @NotBlank(message = "{userregistration.email.required}")
  @Email
  private String email;
  
  @NotBlank(message = "{userregistration.phoneNumber.required}")
  @Size(max = 10, message = "{userregistration.phoneNumber.maxlength}")
  private String phoneNumber;
  
  @NotBlank(message = "{userregistration.firstname.required}")
  @Size(max = 20, message = "{userregistration.firstname.maxlength}")
  private String userFirstName;
  
  @NotBlank(message = "{userregistration.lastname.required}")
  @Size(max = 4, message = "{userregistration.lastname.maxlength}")
  private String userLastName;
  
  //@NotNull(message = "{userregistration.birthdate.required}")
  //@DateTimeFormat(pattern = "DD-MM-YYYY")
  private Date birthDate;
  
  @ValidGender
  private Gender gender;
  
  private String bloodGroup;
  
  @NotNull(message = "{userregistration.country.required}")
  private String country;
  
  public enum Gender{
      MALE,FEMALE,OTHER;
  }
}
