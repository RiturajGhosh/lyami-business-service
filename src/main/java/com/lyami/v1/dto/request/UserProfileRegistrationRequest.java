package com.lyami.v1.dto.request;

import org.springframework.format.annotation.DateTimeFormat;

import com.lyami.v1.validator.ValidGender;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileRegistrationRequest {
  
  private UserProfileAddressRequest address;
  
  @NotBlank(message = "{userregistration.email.required}")
  @Email(message = "{userregistration.email.notvalid}")
  private String email;
  
  @NotBlank(message = "{userregistration.phoneNumber.required}")
  @Size(max = 10, message = "{userregistration.phoneNumber.maxlength}")
  private String phoneNumber;
  
  @NotBlank(message = "{userregistration.firstname.required}")
  @Size(max = 20, message = "{userregistration.firstname.maxlength}")
  private String userFirstName;
  
  @NotBlank(message = "{userregistration.lastname.required}")
  @Size(max = 20, message = "{userregistration.lastname.maxlength}")
  private String userLastName;
  
  @NotBlank(message = "{userregistration.birthdate.required}")
  @DateTimeFormat(pattern = "DD-MM-YYYY")
  private String birthDate;
  
  @ValidGender(anyOf = {Gender.MALE, Gender.FEMALE, Gender.OTHER})
  private Gender gender;
  
  private String bloodGroup;
  
  @NotBlank(message = "{userregistration.country.required}")
  private Integer country;
  
  @Getter
  public enum Gender{
      MALE,FEMALE,OTHER;
  }
}
