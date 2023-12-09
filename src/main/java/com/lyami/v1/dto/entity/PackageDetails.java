package com.lyami.v1.dto.entity;

import com.lyami.v1.dto.entity.commons.Currency;
import com.lyami.v1.dto.entity.commons.Image;
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

    private String packageId;

    private String packagePrice;

    @OneToOne(fetch = FetchType.LAZY)
    private Currency currency;

    private Integer noOfDays;

    private TripType tripType;

    private String itineary;

    private String highlights;

    @Max(value = 5)
    @Min(value = 1)
    private Integer rating;

    private String overview;

    private String title;

    @OneToMany(mappedBy = "packageDetails")
    private List<Image> image;

    private String description;

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
