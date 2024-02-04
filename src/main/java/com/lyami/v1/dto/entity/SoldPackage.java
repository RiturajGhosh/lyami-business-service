package com.lyami.v1.dto.entity;

import com.lyami.v1.dto.entity.user.UserDetails;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.util.Date;
import java.util.Set;

public class SoldPackage  {
    private int soldPackageId;
    private String packageId;
    private Date prfDepatureDate;
    @ManyToOne
    @JoinColumn(name = "soldPackageId", insertable = false, updatable = false)
    private PackageDetails packageDetails;
    Set<UserDetails> userDetailsList;
}
