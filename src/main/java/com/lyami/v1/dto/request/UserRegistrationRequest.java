package com.lyami.v1.dto.request;

import org.springframework.format.annotation.DateTimeFormat;

import com.lyami.v1.dto.request.StayRegistrationImageRequest.ImageRegType;
import com.lyami.v1.validator.ValidGender;

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
public class UserRegistrationRequest {
  private UserRegistrationAddressRequest address;
  
  @NotBlank(message = "{test.key}")
  @Size(max = 50)
  @Email
  private String email;
  
  @NotBlank
  @Size(max = 10)
  private String phoneNumber;
  
  @NotBlank
  @Size(min = 3, max = 20)
  private String userFirstName;
  
  @NotBlank
  @Size(min = 3, max = 20)
  private String userLastName;
  
  @NotBlank
  @DateTimeFormat(pattern = "DD-MM-YYYY")
  private String birthDate;
  
  @ValidGender(anyOf = {Gender.MALE, Gender.FEMALE, Gender.OTHER})
  private Gender gender;
  
  private String bloodGroup;
  
  @NotNull
  private UserCountryRequest country;
  
  @Getter
  public enum Gender{
      MALE,FEMALE,OTHER;
  }
}
