package org.softuni.lease1.domain.model.service;


import org.softuni.lease1.domain.entity.Type;

public class ProfileServiceModel {
    private String id;
    private String fullName;
    private Type type;
    private String UIC;
    private String city;
    private String address;
    private String mobile;
    private UserServiceModel user;

    public ProfileServiceModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getUIC() {
        return UIC;
    }

    public void setUIC(String UIC) {
        this.UIC = UIC;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public UserServiceModel getUser() {
        return user;
    }

    public void setUser(UserServiceModel user) {
        this.user = user;
    }
}
