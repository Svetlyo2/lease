package org.softuni.lease1.domain.model.service;

import org.softuni.lease1.domain.entity.AppStatus;
import org.softuni.lease1.domain.entity.Employee;

import java.time.LocalDateTime;

public class LeaseApplicationServiceModel {
    private String appStatus;
    private Employee employee;
    private LocalDateTime requestDate;
    private LocalDateTime decisionDate;
    private OfferServiceModel offer;

    public LeaseApplicationServiceModel() {
    }

    public String getAppStatus() {
        return appStatus;
    }

    public void setAppStatus(String appStatus) {
        this.appStatus = appStatus;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public LocalDateTime getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(LocalDateTime requestDate) {
        this.requestDate = requestDate;
    }

    public LocalDateTime getDecisionDate() {
        return decisionDate;
    }

    public void setDecisionDate(LocalDateTime decisionDate) {
        this.decisionDate = decisionDate;
    }

    public OfferServiceModel getOffer() {
        return offer;
    }

    public void setOffer(OfferServiceModel offer) {
        this.offer = offer;
    }
}
