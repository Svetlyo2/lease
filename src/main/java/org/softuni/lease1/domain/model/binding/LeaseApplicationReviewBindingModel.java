package org.softuni.lease1.domain.model.binding;

import org.softuni.lease1.domain.model.service.EmployeeServiceModel;

import java.time.LocalDateTime;

public class LeaseApplicationReviewBindingModel {

    private String appStatus;
    private String description;

    public LeaseApplicationReviewBindingModel() {
    }

    public String getAppStatus() {
        return appStatus;
    }

    public void setAppStatus(String appStatus) {
        this.appStatus = appStatus;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
