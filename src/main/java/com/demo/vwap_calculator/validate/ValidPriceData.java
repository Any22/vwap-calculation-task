package com.demo.vwap_calculator.validate;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

public interface ValidPriceData extends Annotation {

    @Target({METHOD, FIELD}) // Indicates the annotation is applicable to Filed of DTO Class
    @Retention(RUNTIME) // Ensures the annotation is available at runtime
    @Constraint(validatedBy = PriceDataValidator.class) // Links to your custom validator
    public @interface ValidatePriceData {

        String message() default "Invalid value"; // Custom error message

        Class<?>[] groups() default {}; // Validation groups

        Class<? extends Payload>[] payload() default {}; // Additional payload for extensibility

        FieldType type();
    }



}