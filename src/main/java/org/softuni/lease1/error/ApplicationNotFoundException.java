package org.softuni.lease1.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Application was not found!")
public class ApplicationNotFoundException extends RuntimeException{

    public ApplicationNotFoundException() {
    }

    public ApplicationNotFoundException(String message) {
        super(message);
    }
}
