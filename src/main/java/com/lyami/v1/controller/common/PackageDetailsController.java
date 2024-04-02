package com.lyami.v1.controller.common;

import com.lyami.v1.dto.response.PackageDetailsResponse;
import com.lyami.v1.service.common.PackageDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("v1/common")
public class PackageDetailsController {

    @Autowired
    PackageDetailsService packageDetailsService;

    @GetMapping("/package/{packageId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<PackageDetailsResponse> getPackageDetailsByPackageId(@PathVariable("packageId") String packageId){
        //log.info("packageId::"+packageId);
        return packageDetailsService.getPackageDetailsByPackageId(packageId);
    }

}
