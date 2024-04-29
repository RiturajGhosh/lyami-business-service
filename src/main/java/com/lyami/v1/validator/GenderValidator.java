package com.lyami.v1.validator;

import com.lyami.v1.dto.request.UserProfileRegistrationRequest.Gender;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class GenderValidator implements ConstraintValidator<ValidGender, Gender> {

	
    @Override
    public void initialize(ValidGender constraint) {
        ConstraintValidator.super.initialize(constraint);
    }

	@Override
    public boolean isValid(Gender value, ConstraintValidatorContext context) {
        return  (value.equals(Gender.MALE) || value.equals(Gender.FEMALE) || value.equals(Gender.OTHER)) ? true : false;
    }
	
}
