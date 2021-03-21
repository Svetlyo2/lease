package org.softuni.lease1.domain.entity;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "cars")
public class Car extends BaseEntity{
    private String make;
    private String carModel;
    private CarCondition carCondition;
    private Integer year;
    private Integer mileage;
    private BigDecimal price;
    private String salesperson;
    private String mobile;
    private String email;
    private String offerUrl;
    private User user;
    private Seller seller;
    private Set<Offer> offers = new HashSet<>();

    public Car() {
    }

    @Column(name = "make", columnDefinition = "TEXT", nullable = false)
    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    @Column(name = "car_model", columnDefinition = "TEXT", nullable = false)
    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "car_condition", columnDefinition = "TEXT", nullable = false)
    public CarCondition getCarCondition() {
        return carCondition;
    }

    public void setCarCondition(CarCondition carCondition) {
        this.carCondition = carCondition;
    }

    @Column(name = "year", nullable = false)
    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    @Column(name = "mileage")
    public Integer getMileage() {
        return mileage;
    }

    public void setMileage(Integer mileage) {
        this.mileage = mileage;
    }

    @DecimalMin("0")
    @Digits(integer=8, fraction=2)
    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Size(min = 5, max = 30, message = "Invalid name")
    @Column(name = "salesperson", columnDefinition = "TEXT")
    public String getSalesperson() {
        return salesperson;
    }


    public void setSalesperson(String salesperson) {
        this.salesperson = salesperson;
    }

    @Column(name = "mobile", columnDefinition = "TEXT", nullable = false)
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Column(name = "email", nullable = false)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "offer_url")
    public String getOfferUrl() {
        return offerUrl;
    }

    public void setOfferUrl(String offerUrl) {
        this.offerUrl = offerUrl;
    }

    @ManyToOne(targetEntity = User.class)
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @ManyToOne(targetEntity = Seller.class, fetch = FetchType.LAZY)
    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    @OneToMany(mappedBy="car")
    public Set<Offer> getOffers() {
        return offers;
    }

    public void setOffers(Set<Offer> offers) {
        this.offers = offers;
    }

}
