package com.lyami.v1.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.lyami.v1.dto.entity.commons.Address;
import com.lyami.v1.dto.entity.user.UserBusinessDetails;
import com.lyami.v1.dto.request.UserRegistrationAddressRequest;
import com.lyami.v1.dto.request.UserRegistrationRequest;

@Mapper
public abstract class UserRegistrationMapper {
	
	
	@Named("mapAddress")
	Address mapAddress(UserRegistrationAddressRequest userRegistrationAddressRequest) {
		Address address = new Address();
		address.setHouseNumber(userRegistrationAddressRequest.getHouseNumber());
		address.setCity(userRegistrationAddressRequest.getCity());
		address.setPincode(userRegistrationAddressRequest.getPincode());
		address.setState(userRegistrationAddressRequest.getState());
		address.setStreet(userRegistrationAddressRequest.getStreet());
		return address;
	}
	
	@Mapping(target = "address", source = "address",  qualifiedByName = "mapAddress")
	public abstract UserBusinessDetails mapUserRegistrationReqtoEntity(UserRegistrationRequest userRegistrationRequest);
}
