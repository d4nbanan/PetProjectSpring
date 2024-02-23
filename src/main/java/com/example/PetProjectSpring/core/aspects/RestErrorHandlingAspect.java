package com.example.PetProjectSpring.core.aspects;

import com.example.PetProjectSpring.core.exceptions.CoreRestException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class RestErrorHandlingAspect {
    @Around("@annotation(com.example.PetProjectSpring.core.annotations.RestErrorHandler)")
    public Object handleErrors(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("ErrorHandler");

        try {
            System.out.println("Try");
            return joinPoint.proceed(); // Execute the original method
        } catch (CoreRestException err) {
            System.out.println("Catch");

            return new ResponseEntity<>(err.getExceptionData(), err.getStatus());
        }
    }
}