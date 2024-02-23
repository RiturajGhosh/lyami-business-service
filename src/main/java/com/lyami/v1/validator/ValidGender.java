package com.lyami.v1.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.lyami.v1.dto.request.UserRegistrationRequest.Gender;

@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = GenderValidator.class)
public @interface ValidGender {
	Gender[] anyOf();
    String message() default "Invalid Gender##101";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
