package com.lyami.v1.mapper;

import com.lyami.v1.dto.entity.PackageDetails;
import com.lyami.v1.dto.entity.commons.Image;
import com.lyami.v1.dto.response.PackageDetailsResponse;
import com.lyami.v1.exception.LyamiBusinessException;
import org.mapstruct.Mapper;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public class PackageDetailsMapper {

    public PackageDetailsResponse generateResponse(PackageDetails packageDetails){

        List<String> imageUri = new ArrayList<>();
        for(Image image : packageDetails.getImage()){
            imageUri.add(image.getImageUri());
        }
        return new PackageDetailsResponse(
                packageDetails.getPackageId(), packageDetails.getPackageName(), packageDetails.getTitle(), packageDetails.getDescription(),
                packageDetails.getOverview(), packageDetails.getHighlights(), packageDetails.getNoOfDays(), packageDetails.getItinerary(),
                packageDetails.getIncludes(), packageDetails.getDestinations(), packageDetails.getPackagePrice(), packageDetails.getRating(),
                packageDetails.getCurrency().getLabel(), packageDetails.getTripType().toString(), packageDetails.getCountry().getCountryName(),
                imageUri);
    }

    public List<PackageDetailsResponse> generateResponse(List<PackageDetails> packageDetailsList){

        if(packageDetailsList == null || packageDetailsList.isEmpty()) throw new LyamiBusinessException("Packages do not exist");

        List<PackageDetailsResponse> packageDetailsResponses = new ArrayList<>(packageDetailsList.size());
        for(PackageDetails p : packageDetailsList){
            packageDetailsResponses.add(generateResponse(p));
        }

        return packageDetailsResponses;
    }
}
