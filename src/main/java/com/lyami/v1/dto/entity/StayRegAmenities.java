package com.lyami.v1.dto.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Getter
@Setter
public class StayRegAmenities implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    private Integer stayRegistrationId;
    private Integer amenitiesId;

    @ManyToOne
    @JoinColumn(name = "stayRegistrationId", referencedColumnName = "id", updatable = false, insertable = false)
    private StayRegistration stayRegistration;

    @ManyToOne
    @JoinColumn(name = "amenitiesId", referencedColumnName = "id", updatable = false, insertable = false)
    private Amenity amenity;

}
