package org.softuni.lease1.domain.model.service;

import org.softuni.lease1.domain.entity.AppStatus;
import org.softuni.lease1.domain.entity.Employee;

import java.time.LocalDateTime;

public class LeaseApplicationServiceModel extends BaseServiceModel {
    private String appStatus;
    private EmployeeServiceModel employee;
    private LocalDateTime requestDate;
    private LocalDateTime decisionDate;
    private String description;
    private OfferServiceModel offer;
    private UserServiceModel user;

    public LeaseApplicationServiceModel() {
    }

    public String getAppStatus() {
        return appStatus;
    }

    public void setAppStatus(String appStatus) {
        this.appStatus = appStatus;
    }

    public EmployeeServiceModel getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeServiceModel employee) {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public OfferServiceModel getOffer() {
        return offer;
    }

    public void setOffer(OfferServiceModel offer) {
        this.offer = offer;
    }

    public UserServiceModel getUser() {
        return user;
    }

    public void setUser(UserServiceModel user) {
        this.user = user;
    }
}
