package com.lyami.v1.common.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class EmailIdValidator implements ConstraintValidator<ValidEmailId, String> {

    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@(.+)$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    @Override
    public boolean isValid(String emailId, ConstraintValidatorContext constraintValidatorContext) {
        return emailId != null && EMAIL_PATTERN.matcher(emailId).matches();
    }
}
