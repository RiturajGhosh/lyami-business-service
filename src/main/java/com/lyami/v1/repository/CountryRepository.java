package com.lyami.v1.repository;

import com.lyami.v1.dto.entity.commons.Country;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CountryRepository extends JpaRepository<Country, Integer> {
    Optional<Country> findByDisplayCode(Integer displayCode);
	Optional<Country> findById(Integer id) ;
}

