package org.softuni.lease1.domain.model.service;

public class NotificationServiceModel extends BaseServiceModel{
    private String type;
    private String message;

    public NotificationServiceModel() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
