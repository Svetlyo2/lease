package org.softuni.lease1.domain.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "sellers")
public class Seller extends BaseEntity{
    private String fullName;
    private String UIC;
    private Type type;
    private String city;
    private String address;
    private Set<Car> cars;

    public Seller() {
    }

    @Column(name = "full_name", columnDefinition = "TEXT", nullable = false)
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Column(name = "UIC", columnDefinition = "TEXT", nullable = false)
    public String getUIC() {
        return UIC;
    }

    public void setUIC(String UIC) {
        this.UIC = UIC;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "type", columnDefinition = "TEXT", nullable = false)
    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
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

    @OneToMany(mappedBy = "seller")
    public Set<Car> getCars() {
        return cars;
    }

    public void setCars(Set<Car> cars) {
        this.cars = cars;
    }
}
