package org.softuni.lease1.domain.model.service;

import org.softuni.lease1.domain.entity.Car;
import org.softuni.lease1.domain.entity.OfferStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OfferServiceModel {
    private String id;
    private Integer deposit;
    private Integer term;
    private Integer residualValue;
    private String status;
    private LocalDateTime requestDate;
    private BigDecimal interest;
    private BigDecimal fee;
    private CarServiceModel car;

    public OfferServiceModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getDeposit() {
        return deposit;
    }

    public void setDeposit(Integer deposit) {
        this.deposit = deposit;
    }

    public Integer getTerm() {
        return term;
    }

    public void setTerm(Integer term) {
        this.term = term;
    }

    public Integer getResidualValue() {
        return residualValue;
    }

    public void setResidualValue(Integer residualValue) {
        this.residualValue = residualValue;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(LocalDateTime requestDate) {
        this.requestDate = requestDate;
    }

    public BigDecimal getInterest() {
        return interest;
    }

    public void setInterest(BigDecimal interest) {
        this.interest = interest;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    public CarServiceModel getCar() {
        return car;
    }

    public void setCar(CarServiceModel car) {
        this.car = car;
    }
}