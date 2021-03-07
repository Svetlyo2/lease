package org.softuni.lease1.domain.entity;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "user_profiles")
public class UserProfile extends BaseEntity{
    private String fullName;
    private Type type;
    private String UIC;
    private String city;
    private String address;
    private String mobile;
    private User user;

    public UserProfile() {
    }

    @Size(min = 5, max = 30, message = "Invalid name")
    @Column(name = "full_name", columnDefinition = "TEXT", nullable = false)
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "type", columnDefinition = "TEXT", nullable = false)
    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Column(name = "UIC", columnDefinition = "TEXT", nullable = false)
    public String getUIC() {
        return UIC;
    }

    public void setUIC(String UIC) {
        this.UIC = UIC;
    }

    @Column(name = "city", columnDefinition = "TEXT", nullable = false)
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Column(name = "address", columnDefinition = "TEXT", nullable = false)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Column(name = "mobile", columnDefinition = "TEXT", nullable = false)
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @OneToOne
    @JoinColumn(name = "user_id")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
