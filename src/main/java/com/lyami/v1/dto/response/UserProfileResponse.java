package com.lyami.v1.dto.response;

import java.util.Date;

import lombok.Data;

@Data
public class UserProfileResponse {
	private UserProfileAddressResponse address;
	private String email;
	private String phoneNumber;
	private String userFirstName;
	private String userLastName;
	private Date birthDate;
	private String gender;
	private String bloodGroup;
	private Integer UUID;
	
}
