package com.lyami.v1.repository;

import com.lyami.v1.dto.entity.StayRegistration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StayRegisterRepository extends JpaRepository<StayRegistration, Integer> {
}
