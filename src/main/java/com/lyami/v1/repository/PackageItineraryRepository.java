package com.lyami.v1.repository;


import com.lyami.v1.dto.entity.PackageDetails;
import com.lyami.v1.dto.entity.PackageItinerary;
import com.lyami.v1.dto.entity.PackageItineraryPrimaryKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PackageItineraryRepository extends JpaRepository<PackageItinerary, PackageItineraryPrimaryKey> {

    List<PackageItinerary> findPackageItinerariesByPackageIdOrderByDayNo(String packageId);
}
