package com.lyami.v1.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = StayRegistrationRequestValidator.class)
public @interface ValidStayRegReq {
    String message() default "Invalid stay registration request##101";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
