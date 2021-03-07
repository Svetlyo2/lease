package org.softuni.lease1.domain.model.view;

import org.softuni.lease1.domain.model.service.OfferServiceModel;

import java.math.BigDecimal;
import java.util.Set;

public class CarListViewModel {
    private String id;
    private String make;
    private String carModel;
    private String carCondition;
    private Integer year;
    private Integer mileage;
    private BigDecimal price;
    private Set<OfferServiceModel> offers;

    public CarListViewModel() {
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

    public String getCarCondition() {
        return carCondition;
    }

    public void setCarCondition(String carCondition) {
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
    public Set<OfferServiceModel> getOffers() {
        return offers;
    }
    public void setOffers(Set<OfferServiceModel> offers) {
        this.offers = offers;
    }

}
