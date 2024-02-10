package com.lyami.v1.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lyami.v1.dto.entity.user.UserBusinessDetails;

public interface UserRegisterRepository extends JpaRepository<UserBusinessDetails, Long>{
	Optional<UserBusinessDetails> findByEmail(String email);
}
