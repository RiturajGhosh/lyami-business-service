package com.lyami.v1.dto.entity;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class PackageItineraryPrimaryKey implements Serializable {
    private String packageId;
    private Integer dayNo;

    // Constructors, getters, setters, equals, and hashCode methods
}