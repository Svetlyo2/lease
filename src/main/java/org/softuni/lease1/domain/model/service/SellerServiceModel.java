package org.softuni.lease1.domain.model.service;
import org.softuni.lease1.domain.entity.Car;
import org.softuni.lease1.domain.entity.Type;

import java.util.Set;

public class SellerServiceModel {
    private String id;
    private String fullName;
    private String UIC;
    private Type type;
    private String city;
    private String address;
    private Set<Car> cars;

    public SellerServiceModel() {
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

    public String getUIC() {
        return UIC;
    }

    public void setUIC(String UIC) {
        this.UIC = UIC;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
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

    public Set<Car> getCars() {
        return cars;
    }

    public void setCars(Set<Car> cars) {
        this.cars = cars;
    }
}
