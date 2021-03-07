package org.softuni.lease1.domain.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "lease_applications")
public class LeaseApplication extends BaseEntity{
    private AppStatus appStatus;
    private Employee employee;
    private LocalDateTime requestDate;
    private LocalDateTime decisionDate;
    private Offer offer;
    private UserProfile user;

    public LeaseApplication() {
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "app_status", columnDefinition = "TEXT", nullable = false)
    public AppStatus getAppStatus() {
        return appStatus;
    }

    public void setAppStatus(AppStatus appStatus) {
        this.appStatus = appStatus;
    }

    @OneToOne
    @JoinColumn(name = "employee_id")
    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    @Column(name = "requestDate", columnDefinition = "TEXT")
    public LocalDateTime getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(LocalDateTime requestDate) {
        this.requestDate = requestDate;
    }

    @Column(name = "decision_date", columnDefinition = "TEXT")
    public LocalDateTime getDecisionDate() {
        return decisionDate;
    }

    public void setDecisionDate(LocalDateTime decisionDate) {
        this.decisionDate = decisionDate;
    }

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "offer_id")
    public Offer getOffer() {
        return offer;
    }

    public void setOffer(Offer offer) {
        this.offer = offer;
    }

    @ManyToOne(targetEntity = UserProfile.class)
    public UserProfile getUser() {
        return user;
    }

    public void setUser(UserProfile user) {
        this.user = user;
    }
}
