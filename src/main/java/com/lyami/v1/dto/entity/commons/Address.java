package com.lyami.v1.dto.entity.commons;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Embeddable
public class Address {
    private String houseNumber;
    private String street;
    private String city;
    private String state;
    private String pincode;
    @ManyToOne
    private Country country;
}
