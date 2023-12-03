package com.lyami.v1.repository;

import com.lyami.v1.dto.entity.Amenity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AmenityRepository extends JpaRepository<Amenity, Integer> {
    Optional<Amenity> findByDisplayCode(Integer displayCode);
}
