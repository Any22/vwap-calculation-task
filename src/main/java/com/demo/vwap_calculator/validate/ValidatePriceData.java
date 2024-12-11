package com.demo.vwap_calculator.validate;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Target({TYPE}) // Indicates the annotation is applicable to class
@Retention(RUNTIME) // Ensures the annotation is available at runtime
@Constraint(validatedBy = PriceDataValidator.class) // Links to your custom validator
public @interface ValidatePriceData {

    String message() default "Invalid value"; // Custom error message

    Class<?>[] groups() default {}; // Validation groups

    Class<? extends Payload>[] payload() default {}; // Additional payload for extensibility
}
