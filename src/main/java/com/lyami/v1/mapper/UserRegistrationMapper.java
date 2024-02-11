package com.lyami.v1.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import com.lyami.v1.dto.entity.commons.Address;
import com.lyami.v1.dto.entity.commons.Country;
import com.lyami.v1.dto.entity.user.UserBusinessDetails;
import com.lyami.v1.dto.request.UserRegistrationAddressRequest;
import com.lyami.v1.dto.request.UserRegistrationRequest;
import com.lyami.v1.exception.LyamiBusinessException;
import com.lyami.v1.repository.CountryRepository;

import lombok.extern.slf4j.Slf4j;

@Mapper
@Slf4j
public abstract class UserRegistrationMapper {
	
	@Autowired
	CountryRepository countryRepository;
	
	@Named("mapCountry")
	Country mapCountry(Integer displayCode) {
		var countryOptional = countryRepository.findByDisplayCode(displayCode);
		log.info("countryOptional::"+countryOptional);
        return countryOptional.orElseThrow(() -> new LyamiBusinessException("Invalid country"));
	}
	
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
	@Mapping(target = "country", source = "country",  qualifiedByName = "mapCountry")
	public abstract UserBusinessDetails mapUserRegistrationReqtoEntity(UserRegistrationRequest userRegistrationRequest);
}
