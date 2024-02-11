package com.lyami.v1.dto.entity.commons;

import java.util.List;

import com.lyami.v1.dto.entity.PackageDetails.TripType;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class Address {
    private String houseNumber;
    private String street;
    private String city;
    private String state;
    private String pincode;
}
