package org.softuni.lease1.domain.model.binding;
import org.softuni.lease1.domain.entity.Offer;
import org.softuni.lease1.domain.entity.User;
import org.softuni.lease1.domain.entity.UserProfile;
import org.softuni.lease1.domain.model.service.OfferServiceModel;
import org.softuni.lease1.domain.model.service.ProfileServiceModel;

import java.time.LocalDateTime;

public class LeaseApplicationAddModel {
    private String appStatus;
    private LocalDateTime requestDate;
    private OfferServiceModel offer;
    private User user;

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

    public OfferServiceModel getOffer() {
        return offer;
    }

    public void setOffer(OfferServiceModel offer) {
        this.offer = offer;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
