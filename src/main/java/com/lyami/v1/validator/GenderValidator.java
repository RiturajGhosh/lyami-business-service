package com.lyami.v1.validator;

import java.util.Arrays;

import com.lyami.v1.dto.request.UserRegistrationRequest.Gender;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class GenderValidator implements ConstraintValidator<ValidGender, Gender> {

	private Gender[] subset;
	
    @Override
    public void initialize(ValidGender constraint) {
        this.subset = constraint.anyOf();
    }

	@Override
    public boolean isValid(Gender value, ConstraintValidatorContext context) {
        return value == null || Arrays.asList(subset).contains(value);
    }
	
}
