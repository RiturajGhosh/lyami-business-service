package com.lyami.v1.repository;

import com.lyami.v1.dto.entity.PackageDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PackageDetailsRepository extends JpaRepository<PackageDetails, Integer> {
    Optional<PackageDetails> findByPackageId(String packageId);

    Optional<PackageDetails[]> findByCountryId(Integer countryId);

    Optional<PackageDetails[]> findByEditionId(Integer editionId);

}
