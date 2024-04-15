package com.lyami.v1.dto.request;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lyami.v1.validator.ValidGender;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserBusinessDetailsUpdateRequest {

    private UserProfileAddressRequest address;

    @NotBlank(message = "{userregistration.email.required}")
    @Email
    private String email;

    @Size(max = 10, message = "{userregistration.phoneNumber.maxlength}")
    private String phoneNumber;

    @Size(max = 20, message = "{userregistration.firstname.maxlength}")
    private String userFirstName;

    @Size(max = 20, message = "{userregistration.lastname.maxlength}")
    private String userLastName;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate birthDate;

    @ValidGender
    private UserBusinessDetailsRegistrationRequest.Gender gender;

    private String bloodGroup;

    //@NotNull(message = "{userregistration.country.required}")
    //private String country;


}
