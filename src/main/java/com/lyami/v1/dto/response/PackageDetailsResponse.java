package com.lyami.v1.dto.response;

import com.lyami.v1.dto.entity.PackageDetails;
import com.lyami.v1.dto.entity.commons.Currency;
import com.lyami.v1.dto.entity.commons.Image;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PackageDetailsResponse {

    private String packageId;
    private String packageName;
    private String title;
    private String description;
    private String overview;
    private String highlights;
    private Integer noOfDays;
    private String itinerary;
    private String includes;
    private String destinations;
    private String packagePrice;
    private Integer rating;
    private String currencyLabel;
    private String tripType;
    private String country;
//    private String edition;
    private String imageUri;
    private String bookingPrice;
}
