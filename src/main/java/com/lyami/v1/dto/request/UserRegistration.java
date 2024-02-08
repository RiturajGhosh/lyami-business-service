package com.lyami.v1.dto.request;

import org.springframework.format.annotation.DateTimeFormat;

import com.lyami.v1.dto.request.StayRegistrationImageRequest.ImageRegType;
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
public class UserRegistration {
  private UserAddress userAddress;
  
  @NotBlank(message = "{test.key}")
  @Size(max = 50)
  @Email
  private String emailID;
  
  @NotBlank
  @Size(max = 10)
  private String phoneNumber;
  
  @NotBlank
  @Size(min = 3, max = 20)
  private String firstName;
  
  @NotBlank
  @Size(min = 3, max = 20)
  private String lastName;
  
  @NotBlank
  @DateTimeFormat(pattern = "DD-MM-YYYY")
  private String dateOfBirth;
  
  @ValidGender(anyOf = {Gender.MALE, Gender.FEMALE, Gender.OTHER})
  private Gender gender;
  
  private String bloodGroup;
  
  @NotBlank
  private String country;
  
  @Getter
  public enum Gender{
      MALE,FEMALE,OTHER;
  }
}
