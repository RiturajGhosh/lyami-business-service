package com.lyami.v1.repository;

import com.lyami.v1.dto.entity.PackageDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PackageDetailsRepository extends JpaRepository<PackageDetails, Integer> {
    Optional<PackageDetails> findByPackageId(String packageId);

    @Override
    List<PackageDetails> findAll();
    List<PackageDetails> findByCountryId(Integer country);

    List<PackageDetails> findByCountryIdAndNoOfDays(Integer countryId, Integer noOfDays);

    List<PackageDetails> findByNoOfDays(Integer noOfDays);

    List<PackageDetails> findByEditionId(Integer editionId);

    List<PackageDetails> findByIsPopular(Boolean isPopular);

    List<PackageDetails> findByDestinationsLike(String destination);

}
