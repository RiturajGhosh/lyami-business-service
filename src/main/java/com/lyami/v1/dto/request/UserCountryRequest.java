package com.lyami.v1.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCountryRequest {
	
	@NotBlank
	private Integer displayCode;
	
	@NotBlank
    private String countryName;
}
