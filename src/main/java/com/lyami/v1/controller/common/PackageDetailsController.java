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

@RestController
@CrossOrigin
@RequestMapping("v1/common")
@Slf4j
public class PackageDetailsController {

    @Autowired
    PackageDetailsService packageDetailsService;

    @GetMapping("/package/{packageId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<PackageDetailsResponse> getPackageDetailsByPackageId(@PathVariable("packageId") String packageId){
        //log.info("packageId::"+packageId);
        return packageDetailsService.getPackageDetailsByPackageId(packageId);
    }

    @GetMapping("/package")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<PackageDetailsResponse>> getFilteredPackageDetails(@RequestParam(required = false) Integer countryId, @RequestParam(required = false) Integer noOfDays){
        //log.info("packageId::"+packageId);
        return packageDetailsService.getFilteredPackageDetails(countryId, noOfDays);
    }

    @GetMapping("/package/edition/{editionId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<PackageDetailsResponse>> getPackageDetailsByEditionId(@PathVariable("editionId") Integer editionId){

        return packageDetailsService.getPackageDetailsByEditionId(editionId);
    }

    @GetMapping("/package/popular")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<PackageDetailsResponse>> getPackageDetailsByEditionId(){

        return packageDetailsService.getPopularPackages(true);
    }

    @GetMapping("/package/destination")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<PackageDetailsResponse>> getPackageDetailsByDestination(@RequestParam @NotBlank String destination){

        return packageDetailsService.getPackageDetailsByDestination(destination);
    }

}
