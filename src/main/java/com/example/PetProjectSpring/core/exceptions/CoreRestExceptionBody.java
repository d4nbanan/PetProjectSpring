package com.example.PetProjectSpring.core.exceptions;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Data
public class CoreRestExceptionBody {
    private HttpStatus status;
    private String message;
    private Date timestamp;
}
