package org.softuni.lease1.domain.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "offers")
public class Offer extends BaseEntity {

    private Integer deposit;
    private Integer term;
    private Integer residualValue;
    private String status;
    private LocalDateTime requestDate;
    private BigDecimal interest;
    private BigDecimal fee;
    private Car car;

    public Offer() {
    }

    @Column(name = "deposit", nullable = false)
    public Integer getDeposit() {
        return deposit;
    }

    public void setDeposit(Integer deposit) {
        this.deposit = deposit;
    }

    @Column(name = "term", nullable = false)
    public Integer getTerm() {
        return term;
    }

    public void setTerm(Integer term) {
        this.term = term;
    }

    @Column(name = "residual_value", nullable = false)
    public Integer getResidualValue() {
        return residualValue;
    }

    public void setResidualValue(Integer residualValue) {
        this.residualValue = residualValue;
    }

    @Column(name = "status", columnDefinition = "TEXT", nullable = false)
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Column(name = "request_date", columnDefinition = "TEXT", nullable = false)
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

    //    @ManyToOne(fetch = FetchType.LAZY, optional=false)
//    @JoinColumn(name="car_id", nullable=false)

    @ManyToOne(targetEntity = Car.class)
    @JoinColumn(name="car_id", nullable=false)
    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

}
