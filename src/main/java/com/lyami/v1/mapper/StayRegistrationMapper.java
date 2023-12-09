package com.lyami.v1.mapper;

import com.lyami.v1.dto.entity.commons.Country;
import com.lyami.v1.dto.entity.stayregistration.StayRegAmenities;
import com.lyami.v1.dto.entity.stayregistration.StayRegistration;
import com.lyami.v1.dto.request.StayRegistrationRequest;
import com.lyami.v1.exception.LyamiBusinessException;
import com.lyami.v1.repository.AmenityRepository;
import com.lyami.v1.repository.CountryRepository;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class StayRegistrationMapper {

    @Autowired
    protected CountryRepository countryRepository;

    @Autowired
    protected AmenityRepository amenityRepository;

    @Named("mapStayType")
    Integer mapStayTypeCode(StayRegistrationRequest stayRegistrationRequest) {
        return stayRegistrationRequest.getStayType().getCode();
    }

    @Named("mapCountry")
    Country mapCountryFromCountryDispCode(Integer displayCode) {
        var countryOptional = countryRepository.findByDisplayCode(displayCode);
        return countryOptional.orElseThrow(() -> new LyamiBusinessException("Invalid country"));
    }

    @Named("mapAmenity")
    Set<StayRegAmenities> mapAmenity(Set<StayRegistrationRequest.Amenities> amenities) {
        var amenityEntitySet = amenities
                .stream()
                .map(amenity ->
                        amenityRepository.findByDisplayCode(amenity.getDisplayCode())
                                .orElseThrow(() -> new LyamiBusinessException("invalid Amenity")))
                .collect(Collectors.toSet());
        return amenityEntitySet.stream().map(amenity ->{
            var stayRegAmenity = new StayRegAmenities();
            stayRegAmenity.setAmenity(amenity);
            stayRegAmenity.setAmenitiesId(amenity.getId());
            return stayRegAmenity;
        }).collect(Collectors.toSet());

    }

    @Mapping(target = "stayTypeCode", source = "stayRegistrationRequest", qualifiedByName = "mapStayType")
    @Mapping(target = "country", source = "countryDisplayCode", qualifiedByName = "mapCountry")
    @Mapping(target = "stayRegAmenities", source = "amenities", qualifiedByName = "mapAmenity")
    public abstract StayRegistration mapStayRegistrationReqToEntity(StayRegistrationRequest stayRegistrationRequest);
}
