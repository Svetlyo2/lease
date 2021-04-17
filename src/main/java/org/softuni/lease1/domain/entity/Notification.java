package org.softuni.lease1.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "notifications")
public class Notification extends BaseEntity{

    private String type;
    private String message;

    public Notification() {
    }

    @Column(name = "type", columnDefinition = "TEXT", nullable = false,unique = true)
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Column(name = "message", columnDefinition = "TEXT")
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}