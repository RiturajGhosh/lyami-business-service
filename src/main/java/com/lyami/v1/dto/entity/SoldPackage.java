package com.lyami.v1.dto.entity;

import com.lyami.v1.dto.entity.user.UserBusinessDetails;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

@Entity
@Getter
@Setter
public class SoldPackage  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int soldPackageId;
    private String packageId;
    private Date prfDepatureDate;
    @ManyToOne
    @JoinColumn(name = "soldPackageId", referencedColumnName = "id", insertable = false, updatable = false)
    private PackageDetails packageDetails;
    @ManyToMany(mappedBy = "soldPackageSet")
    Set<UserBusinessDetails> userBusinessDetailsList;
    private Boolean isCancelled;

}
