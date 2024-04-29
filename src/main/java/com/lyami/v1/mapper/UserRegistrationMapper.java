package com.lyami.v1.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import com.lyami.v1.dto.entity.commons.Address;
import com.lyami.v1.dto.entity.commons.Country;
import com.lyami.v1.dto.entity.user.UserBusinessDetails;
import com.lyami.v1.dto.request.UserProfileAddressRequest;
import com.lyami.v1.dto.request.UserProfileRegistrationRequest;
import com.lyami.v1.dto.response.UserProfileAddressResponse;
import com.lyami.v1.dto.response.UserProfileResponse;
import com.lyami.v1.exception.LyamiBusinessException;
import com.lyami.v1.repository.CountryRepository;

import lombok.extern.slf4j.Slf4j;

@Mapper(componentModel = "spring")
@Slf4j
public abstract class UserRegistrationMapper {
	
	@Autowired
	CountryRepository countryRepository;
	
	@Named("mapCountry")
	Country mapCountry(Integer displayCode) {
		log.info("displayCode-->"+displayCode);
		var countryOptional = countryRepository.findByDisplayCode(displayCode);
        return countryOptional.orElseThrow(() -> new LyamiBusinessException("Invalid country"));
	}
	
	@Named("mapAddressRequest")
	Address mapAddressRequest(UserProfileAddressRequest userRegistrationAddressRequest) {
		Address address = new Address();
		address.setHouseNumber(userRegistrationAddressRequest.getHouseNumber());
		address.setCity(userRegistrationAddressRequest.getCity());
		address.setPincode(userRegistrationAddressRequest.getPincode());
		address.setState(userRegistrationAddressRequest.getState());
		address.setStreet(userRegistrationAddressRequest.getStreet());
		address.setPostOffice(userRegistrationAddressRequest.getPostOffice());
		address.setPoliceStation(userRegistrationAddressRequest.getPoliceStation());
		return address;
	}
	
	@Named("mapAddressResponse")
	UserProfileAddressResponse mapAddressRequest(Address address) {
		UserProfileAddressResponse userProfileAddressResponse = new UserProfileAddressResponse();
		userProfileAddressResponse.setHouseNumber(address.getHouseNumber());
		userProfileAddressResponse.setCity(address.getCity());
		userProfileAddressResponse.setPincode(address.getPincode());
		userProfileAddressResponse.setState(address.getState());
		userProfileAddressResponse.setStreet(address.getStreet());
		userProfileAddressResponse.setPostOffice(address.getPostOffice());
		userProfileAddressResponse.setPoliceStation(address.getPoliceStation());
		return userProfileAddressResponse;
	}
	
	@Mapping(target = "address", source = "address",  qualifiedByName = "mapAddressRequest")
	//@Mapping(target = "country", source = "country",  qualifiedByName = "mapCountry")
	public abstract UserBusinessDetails mapUserProfileRegistrationReqtoEntity(UserProfileRegistrationRequest userRegistrationRequest);
	
	@Mapping(target = "userProfileAddressResponse", source = "address",  qualifiedByName = "mapAddressResponse")
	public abstract UserProfileResponse mapUserBusinessDeatailstoUserProfileResponse(UserBusinessDetails UserBusinessDetails);
}
