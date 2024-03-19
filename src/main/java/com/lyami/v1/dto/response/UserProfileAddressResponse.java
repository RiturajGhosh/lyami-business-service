package com.lyami.v1.dto.response;

import lombok.Data;

@Data
public class UserProfileAddressResponse {
	private String houseNumber;
	private String street;
	private String city;
	private String state;
	private String pincode;
	private String postOffice;
	private String policeStation;
}
