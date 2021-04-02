package org.softuni.lease1.web.controllers.api;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class OfferListResponseModel {
    private String id;
    private String requestDate;

    public OfferListResponseModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(String requestDate) {
        this.requestDate = requestDate;
    }
}
