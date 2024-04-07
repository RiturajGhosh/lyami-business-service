package com.lyami.v1.service.common;

import com.lyami.v1.dto.response.PackageDetailsResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PackageDetailsService {


    ResponseEntity<PackageDetailsResponse> getPackageDetailsByPackageId(String packageId);

    ResponseEntity<List<PackageDetailsResponse>> getFilteredPackageDetails(Integer countryId, Integer noOfDays);

    ResponseEntity<List<PackageDetailsResponse>> getPackageDetailsByEditionId(Integer editionId);

    ResponseEntity<List<PackageDetailsResponse>> getPopularPackages(Boolean isPopular);

    ResponseEntity<List<PackageDetailsResponse>> getPackageDetailsByDestination(String destination);

}
