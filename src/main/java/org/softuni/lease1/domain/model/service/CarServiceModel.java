package org.softuni.lease1.domain.model.service;

import org.softuni.lease1.domain.entity.CarCondition;

import java.math.BigDecimal;
import java.util.Set;

public class CarServiceModel {
    private String id;
    private String make;
    private String carModel;
    private CarCondition carCondition;
    private Integer year;
    private Integer mileage;
    private BigDecimal price;
    private String salesperson;
    private String mobile;
    private String email;
    private UserServiceModel user;
    private SellerServiceModel seller;
    private Set<OfferServiceModel> offers;

    public CarServiceModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public CarCondition getCarCondition() {
        return carCondition;
    }

    public void setCarCondition(CarCondition carCondition) {
        this.carCondition = carCondition;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMileage() {
        return mileage;
    }

    public void setMileage(Integer mileage) {
        this.mileage = mileage;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getSalesperson() {
        return salesperson;
    }

    public void setSalesperson(String salesperson) {
        this.salesperson = salesperson;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserServiceModel getUser() {
        return user;
    }

    public void setUser(UserServiceModel user) {
        this.user = user;
    }

    public SellerServiceModel getSeller() {
        return seller;
    }

    public void setSeller(SellerServiceModel seller) {
        this.seller = seller;
    }

    public Set<OfferServiceModel> getOffers() {
        return offers;
    }

    public void setOffers(Set<OfferServiceModel> offers) {
        this.offers = offers;
    }
}
