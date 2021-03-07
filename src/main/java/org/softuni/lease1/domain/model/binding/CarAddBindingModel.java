package org.softuni.lease1.domain.model.binding;

import org.softuni.lease1.common.Constants;
import org.softuni.lease1.domain.entity.CarCondition;

import javax.validation.constraints.*;
import java.math.BigDecimal;


public class CarAddBindingModel {
    private String make;
    private String carModel;
    private CarCondition carCondition;
    private Integer year;
    private Integer mileage;
    private BigDecimal price;
    private String salesperson;
    private String mobile;
    private String email;

    public CarAddBindingModel() {
    }

    @NotNull
    @NotEmpty(message = Constants.EMPTY_FIELD)
    @NotBlank(message = Constants.EMPTY_FIELD)
    @Size(min = 2, max = 15, message = "Invalid make!")
    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    @NotNull
    @NotEmpty(message = Constants.EMPTY_FIELD)
    @NotBlank(message = Constants.EMPTY_FIELD)
    @Size(min = 1, max = 10, message = "Invalid model!")
    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    @NotNull(message = "Please select car condition")
    public CarCondition getCarCondition() {
        return carCondition;
    }

    public void setCarCondition(CarCondition carCondition) {
        this.carCondition = carCondition;
    }

    @NotNull(message = "Please fill year of first registration")
    @Min(value = 2010, message = "We could not finance cars registered before 2010")
    @Max(value = 2021, message = "Year of registration could not be after current year")
    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    @NotNull(message = "Please fill mileage")
    @Min(value = 0, message = "Mileage could not be negative")
    @Max(value = 200000, message = "We could not finance cars with mileage more than 200 000km")
    public Integer getMileage() {
        return mileage;
    }

    public void setMileage(Integer mileage) {
        this.mileage = mileage;
    }

    @NotNull
    @Positive(message = "Price must be at least 0.")
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @NotNull
    @NotEmpty(message = Constants.EMPTY_FIELD)
    @NotBlank(message = Constants.EMPTY_FIELD)
    @Size(min = 5, max = 30, message = "Invalid name!")
    public String getSalesperson() {
        return salesperson;
    }

    public void setSalesperson(String salesperson) {
        this.salesperson = salesperson;
    }
    @NotNull
    @NotEmpty(message = Constants.EMPTY_FIELD)
    @NotBlank(message = Constants.EMPTY_FIELD)
    @Size(min = 7, max = 17, message = "Invalid mobile number!")
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @NotNull
    @NotEmpty(message = Constants.EMPTY_FIELD)
    @NotBlank(message = Constants.EMPTY_FIELD)
    @Email(message = "Please enter valid email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}


