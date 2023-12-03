package com.lyami.v1.dto.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@ToString
@Entity
public class StayRegistration implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    private String stayName;
    private Integer stayTypeCode;
    @ManyToOne(fetch = FetchType.LAZY)
    private Country country;
    private Integer pincode;
    private String address;
    private String contactNumber;
    private String emailAddress;
    private String hostName;
    private String marketingContact;
    private String opsContact;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "stayRegistration")
    private Set<StayRegAmenities> stayRegAmenities;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "stayRegistration")
    private List<StayRegImage> stayRegImages;
}
