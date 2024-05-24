package com.lyami.v1.dto.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Entity
@IdClass(PackageItineraryPrimaryKey.class)
public class PackageItinerary {
    @Id
    private String packageId;

    @Id
    private Integer dayNo;

    private String heading;

    @Column(length = 1000, columnDefinition = "TEXT")
    private String morning;

    @Column(length = 1000, columnDefinition = "TEXT")
    private String afternoon;

    @Column(length = 1000, columnDefinition = "TEXT")
    private String evening;

    @Column(length = 1000, columnDefinition = "TEXT")
    private String night;

    @Column(length = 1000, columnDefinition = "TEXT")
    private String shortDesc;

    private String location;

}






