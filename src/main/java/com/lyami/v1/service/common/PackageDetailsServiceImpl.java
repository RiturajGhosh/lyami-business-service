package com.lyami.v1.service.common;

import com.lyami.v1.dto.entity.PackageDetails;
import com.lyami.v1.dto.entity.commons.Image;
import com.lyami.v1.dto.response.PackageDetailsResponse;
import com.lyami.v1.exception.LyamiBusinessException;
import com.lyami.v1.repository.PackageDetailsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class PackageDetailsServiceImpl implements PackageDetailsService {

    private PackageDetailsRepository packageDetailsRepository;

    @Autowired
    public PackageDetailsServiceImpl(PackageDetailsRepository packageDetailsRepository) {
        super();
        this.packageDetailsRepository = packageDetailsRepository;
    }

    @Override
    public ResponseEntity<PackageDetailsResponse> getPackageDetailsByPackageId(String packageId){
        var packageDetails = packageDetailsRepository.findByPackageId(
                packageId).orElseThrow(()->
                new LyamiBusinessException("Package does not exists"));
        log.info("packageDetails::"+packageDetails);

        return ResponseEntity.ok(generateResponse(packageDetails));
    }

    @Override
    public ResponseEntity<List<PackageDetailsResponse>> getFilteredPackageDetails(Integer countryId, Integer noOfDays){
        List<PackageDetails> packageDetailsList;

        if(countryId != null && noOfDays != null){
            packageDetailsList = packageDetailsRepository.findByCountryIdAndNoOfDays(countryId, noOfDays);
        } else if(countryId != null){
            packageDetailsList = packageDetailsRepository.findByCountryId(countryId);
        } else if(noOfDays != null){
            packageDetailsList = packageDetailsRepository.findByNoOfDays(noOfDays);
        } else {
            packageDetailsList = packageDetailsRepository.findAll();
        }

        return ResponseEntity.ok(generateResponse(packageDetailsList));
    }

    @Override
    public ResponseEntity<List<PackageDetailsResponse>> getPackageDetailsByEditionId(Integer editionId){
        List<PackageDetails> packageDetailsList = packageDetailsRepository.findByEditionId(editionId);

        return ResponseEntity.ok(generateResponse(packageDetailsList));
    }

    @Override
    public ResponseEntity<List<PackageDetailsResponse>> getPopularPackages(Boolean isPopular){
        List<PackageDetails> packageDetailsList = packageDetailsRepository.findByIsPopular(true);

        return ResponseEntity.ok(generateResponse(packageDetailsList));
    }

    @Override
    public ResponseEntity<List<PackageDetailsResponse>> getPackageDetailsByDestination(String destination){
        List<PackageDetails> packageDetailsList = packageDetailsRepository.findByDestinationsLike("%" + destination + "%");

        return ResponseEntity.ok(generateResponse(packageDetailsList));
    }


    private PackageDetailsResponse generateResponse(PackageDetails packageDetails){

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

    private List<PackageDetailsResponse> generateResponse(List<PackageDetails> packageDetailsList){

        if(packageDetailsList == null || packageDetailsList.size() == 0) throw new LyamiBusinessException("Packages do not exist");

        List<PackageDetailsResponse> packageDetailsResponses = new ArrayList<>(packageDetailsList.size());
        for(PackageDetails p : packageDetailsList){
            packageDetailsResponses.add(generateResponse(p));
        }

        return packageDetailsResponses;
    }

}
