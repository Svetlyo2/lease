package org.softuni.lease1.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Car was not found!")
public class CarNotFoundException extends RuntimeException{

    public CarNotFoundException() {
    }

    public CarNotFoundException(String message) {
        super(message);
    }
}
