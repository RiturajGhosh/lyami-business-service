package com.lyami.v1.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lyami.v1.dto.entity.user.UserBusinessDetails;

@Repository
public interface UserBusinessDetailsRepository extends JpaRepository<UserBusinessDetails, Long>{
	Optional<UserBusinessDetails> findByEmail(String email);
}
