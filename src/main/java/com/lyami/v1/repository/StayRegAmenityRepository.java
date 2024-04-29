package com.lyami.v1.repository;

import com.lyami.v1.dto.entity.stayregistration.StayRegAmenities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StayRegAmenityRepository extends JpaRepository<StayRegAmenities, Integer> {
}
