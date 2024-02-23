package com.example.PetProjectSpring.core.exceptions;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Data
public class CoreRestException extends Exception {
    private HttpStatus status;
    private String message;
    private Date timestamp;

    public CoreRestException(HttpStatus status, String message) {
        super(message);

        this.status = status;
        this.message = message;
        this.timestamp = new Date();
    }

    public CoreRestExceptionBody getExceptionData() {
        CoreRestExceptionBody body = new CoreRestExceptionBody();
        body.setTimestamp(this.timestamp);
        body.setStatus(this.status);
        body.setMessage(this.message);

        return body;
    }
}
