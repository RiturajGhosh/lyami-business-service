package com.lyami.v1.dto.entity.user;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class UserUUIDSequence {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer UUID;
}
