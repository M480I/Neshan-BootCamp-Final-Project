package com.etaxi.core.location.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = LocationPairValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface LocationPairValidation {
    String message() default "Invalid location coordinates";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
