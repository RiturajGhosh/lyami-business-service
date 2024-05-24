package com.lyami.v1.dto.response;

import com.lyami.v1.dto.entity.PackageDetails;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;


@Data
@AllArgsConstructor
public class SoldPackageResponse {

    private String packageId;
    private Date prfDepatureDate;
    private PackageDetailsResponse packageDetailsResponse;

}

