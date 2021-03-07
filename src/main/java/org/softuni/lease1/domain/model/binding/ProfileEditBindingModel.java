package org.softuni.lease1.domain.model.binding;

import org.softuni.lease1.common.Constants;
import org.softuni.lease1.domain.entity.Type;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ProfileEditBindingModel {
    private String fullName;
    private Type type;
    private String UIC;
    private String city;
    private String address;
    private String mobile;

    public ProfileEditBindingModel() {
    }

    @NotNull
    @NotEmpty(message = Constants.EMPTY_FIELD)
    @NotBlank(message = Constants.EMPTY_FIELD)
    @Size(min = 5, max = 30, message = "Invalid name!")
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @NotNull(message = "Please select type")
    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
    @NotNull
    @NotEmpty
    @Size(min = 10, max = 13, message = "Invalid identification code!")
    public String getUIC() {
        return UIC;
    }

    public void setUIC(String UIC) {
        this.UIC = UIC;
    }

    @NotNull
    @NotEmpty(message = Constants.EMPTY_FIELD)
    @NotBlank(message = Constants.EMPTY_FIELD)
    @Size(min = 3, max = 20, message = "Invalid city name!")
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @NotNull
    @NotEmpty(message = Constants.EMPTY_FIELD)
    @NotBlank(message = Constants.EMPTY_FIELD)
    @Size(min = 5, max = 30, message = "Invalid address!")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
}
