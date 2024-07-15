package com.lyami.v1.controller.common;

import com.lyami.v1.dto.response.PackageDetailsResponse;
import com.lyami.v1.service.common.PackageDetailsService;
import jakarta.validation.constraints.NotBlank;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/v1/common")
@Slf4j
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PackageDetailsController {

    @Autowired
    private PackageDetailsService packageDetailsService;

    @GetMapping("/package/{packageId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<PackageDetailsResponse> getPackageDetailsByPackageId(
            @PathVariable("packageId") String packageId) {
        return packageDetailsService.getPackageDetailsByPackageId(packageId);
    }

    @CrossOrigin(origins = "*") // Allow all origins. Modify as needed.
    @GetMapping("/package")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<PackageDetailsResponse>> getFilteredPackageDetails(
            @RequestParam(required = false) Long countryId, @RequestParam(required = false) Integer noOfDays) {
        return packageDetailsService.getFilteredPackageDetails(countryId, noOfDays);
    }

    @GetMapping("/package/edition/{editionId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<PackageDetailsResponse>> getPackageDetailsByEditionId(
            @PathVariable("editionId") Long editionId) {
        return packageDetailsService.getPackageDetailsByEditionId(editionId);
    }

    @CrossOrigin(origins = "*") // Allow all origins. Modify as needed.
    @GetMapping("/package/popular")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<PackageDetailsResponse>> getPopularPackages() {
        return packageDetailsService.getPopularPackages(true);
    }

    @GetMapping("/package/destination")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<PackageDetailsResponse>> getPackageDetailsByDestination(
            @RequestParam @NotBlank String destination) {
        return packageDetailsService.getPackageDetailsByDestination(destination);
    }

    @GetMapping("/package/non_indian")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<PackageDetailsResponse>> getNonIndianPackageDetails() {
        return packageDetailsService.getNonIndianPackageDetails();
    }
}
