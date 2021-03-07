package org.softuni.lease1.domain.model.binding;
import org.softuni.lease1.domain.entity.Offer;
import org.softuni.lease1.domain.entity.UserProfile;

import java.time.LocalDateTime;

public class LeaseApplicationAddModel {
    private String appStatus;
    private LocalDateTime requestDate;
    private Offer offer;
    private UserProfile user;

    public LeaseApplicationAddModel() {
    }

    public String getAppStatus() {
        return appStatus;
    }

    public void setAppStatus(String appStatus) {
        this.appStatus = appStatus;
    }

    public LocalDateTime getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(LocalDateTime requestDate) {
        this.requestDate = requestDate;
    }

    public Offer getOffer() {
        return offer;
    }

    public void setOffer(Offer offer) {
        this.offer = offer;
    }

    public UserProfile getUser() {
        return user;
    }

    public void setUser(UserProfile user) {
        this.user = user;
    }
}
