package com.lyami.v1.dto.entity;

import com.lyami.v1.dto.entity.commons.Currency;
import com.lyami.v1.dto.entity.commons.Country;
import com.lyami.v1.dto.entity.commons.Edition;
import com.lyami.v1.dto.entity.commons.Image;
import com.lyami.v1.dto.entity.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Entity
public class PackageDetails implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String packageId;

    @Column(unique = true)
    private String packageName;

    private String title;

    @Column(length = 1000, columnDefinition = "TEXT")
    private String description;

    @Column(length = 1000, columnDefinition = "TEXT")
    private String overview;

    @Column(length = 1000, columnDefinition = "TEXT")
    private String highlights;

    private Integer noOfDays;

    @Column(length = 2000)
    private String itinerary;

    @Column(length = 1000, columnDefinition = "TEXT")
    private String includes;

    @Column(length = 1000, columnDefinition = "TEXT")
    private String destinations;

    private String packagePrice;

    @Max(value = 5)
    @Min(value = 1)
    private Integer rating;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "currencyId")
    private Currency currency;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "countryId")
    private Country country;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "editionId")
    private Edition edition;

    private TripType tripType;

    @OneToMany(mappedBy = "packageDetails")
    private List<Image> image;

    private Boolean isPopular;

    @OneToMany
    @JoinColumn(name = "packageId", referencedColumnName = "packageId", insertable = false, updatable = false )
    private List<PackageItinerary> packageItinerary;

    public enum TripType {

        SOLO(1, "solo"), BACKPACKER(2, "backpacker"), ALL(3, "all");
        private Integer value;
        private String label;

        TripType(Integer value, String label) {
            this.value = value;
            this.label = label;
        }
    }
}
