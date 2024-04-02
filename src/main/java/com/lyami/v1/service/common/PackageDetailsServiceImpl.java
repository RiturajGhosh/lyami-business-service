package com.lyami.v1.service.common;

import com.lyami.v1.dto.entity.PackageDetails;
import com.lyami.v1.dto.entity.commons.Image;
import com.lyami.v1.dto.response.PackageDetailsResponse;
import com.lyami.v1.exception.LyamiBusinessException;
import com.lyami.v1.repository.PackageDetailsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class PackageDetailsServiceImpl implements PackageDetailsService {

    private PackageDetailsRepository packageDetailsRepository;

    @Autowired
    public PackageDetailsServiceImpl(PackageDetailsRepository packageDetailsRepository) {
        super();
        this.packageDetailsRepository = packageDetailsRepository;
    }

    @Override
    public ResponseEntity<PackageDetailsResponse> getPackageDetailsByPackageId(String packageId){
        var packageDetails = packageDetailsRepository.findByPackageId(
                packageId).orElseThrow(()->
                new LyamiBusinessException("Package does not exists"));
        log.info("packageDetails::"+packageDetails);

        return ResponseEntity.ok(generateResponse(packageDetails));
    }

    private PackageDetailsResponse generateResponse(PackageDetails packageDetails){
        return new PackageDetailsResponse(
                packageDetails.getPackageId(), packageDetails.getPackageName(), packageDetails.getTitle(), packageDetails.getDescription(),
                packageDetails.getOverview(), packageDetails.getHighlights(), packageDetails.getNoOfDays(), packageDetails.getItinerary(),
                packageDetails.getIncludes(), packageDetails.getDestinations(), packageDetails.getPackagePrice(), packageDetails.getRating(),
                packageDetails.getCurrency().getLabel(), packageDetails.getTripType().toString(), packageDetails.getCountry().getCountryName());
    }

}
