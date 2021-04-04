package org.softuni.lease1.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Offer was not found!")
public class OfferNotFoundException extends RuntimeException{

    public OfferNotFoundException() {
    }

    public OfferNotFoundException(String message) {
        super(message);
    }
}
