package org.softuni.lease1.domain.model.view;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OfferRequestViewModel {
    private String id;
    private Integer deposit;
    private Integer term;
    private Integer residualValue;
    private String  status;
    private LocalDateTime requestDate;
    private String make;
    private BigDecimal price;
    private String carId;

    public OfferRequestViewModel() {
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

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }
}
