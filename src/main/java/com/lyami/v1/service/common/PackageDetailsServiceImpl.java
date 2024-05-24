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
import com.lyami.v1.mapper.PackageDetailsMapper;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class PackageDetailsServiceImpl implements PackageDetailsService {

    private PackageDetailsRepository packageDetailsRepository;
    private PackageDetailsMapper packageDetailsMapper;

    @Autowired
    public PackageDetailsServiceImpl(PackageDetailsRepository packageDetailsRepository, PackageDetailsMapper packageDetailsMapper) {
        super();
        this.packageDetailsRepository = packageDetailsRepository;
        this.packageDetailsMapper = packageDetailsMapper;
    }

    @Override
    public ResponseEntity<PackageDetailsResponse> getPackageDetailsByPackageId(String packageId){
        var packageDetails = packageDetailsRepository.findByPackageId(
                packageId).orElseThrow(()->
                new LyamiBusinessException("Package does not exists"));
        log.info("packageDetails::"+packageDetails);

        return ResponseEntity.ok(packageDetailsMapper.generateResponse(packageDetails));
    }

    @Override
    public ResponseEntity<List<PackageDetailsResponse>> getFilteredPackageDetails(Long countryId, Integer noOfDays){
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

        return ResponseEntity.ok(packageDetailsMapper.generateResponse(packageDetailsList));
    }

    @Override
    public ResponseEntity<List<PackageDetailsResponse>> getPackageDetailsByEditionId(Long editionId){
        List<PackageDetails> packageDetailsList = packageDetailsRepository.findByEditionId(editionId);

        return ResponseEntity.ok(packageDetailsMapper.generateResponse(packageDetailsList));
    }

    @Override
    public ResponseEntity<List<PackageDetailsResponse>> getPopularPackages(Boolean isPopular){
        List<PackageDetails> packageDetailsList = packageDetailsRepository.findByIsPopular(true);

        return ResponseEntity.ok(packageDetailsMapper.generateResponse(packageDetailsList));
    }

    @Override
    public ResponseEntity<List<PackageDetailsResponse>> getPackageDetailsByDestination(String destination){
        List<PackageDetails> packageDetailsList = packageDetailsRepository.findByDestinationsLike("%" + destination + "%");

        return ResponseEntity.ok(packageDetailsMapper.generateResponse(packageDetailsList));
    }

    @Override
    public ResponseEntity<List<PackageDetailsResponse>> getNonIndianPackageDetails(){
        List<PackageDetails> packageDetailsList = packageDetailsRepository.findByCountryIdNot(1L);

        return ResponseEntity.ok(packageDetailsMapper.generateResponse(packageDetailsList));
    }




}
