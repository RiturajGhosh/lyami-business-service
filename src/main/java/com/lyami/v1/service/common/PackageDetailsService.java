package com.lyami.v1.service.common;

import com.lyami.v1.dto.response.PackageDetailsResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface PackageDetailsService {


    ResponseEntity<PackageDetailsResponse> getPackageDetailsByPackageId(String packageId);


}
