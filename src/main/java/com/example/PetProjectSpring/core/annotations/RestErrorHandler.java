package com.example.PetProjectSpring.core.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD) // Apply to methods
@Retention(RetentionPolicy.RUNTIME) // Retain at runtime
public @interface RestErrorHandler {
    // You can add optional attributes if needed, such as:
    // Class<? extends Throwable>[] value() default {}; // Specific exception types to handle
    // HttpStatus status() default HttpStatus.BAD_REQUEST; // Default HTTP status
}