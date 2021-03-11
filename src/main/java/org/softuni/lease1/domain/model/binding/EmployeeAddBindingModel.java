package org.softuni.lease1.domain.model.binding;

import org.softuni.lease1.common.Constants;
import org.softuni.lease1.domain.entity.Type;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class EmployeeAddBindingModel {
    private String username;
    private String fullName;
    private String department;
    private String position;

    public EmployeeAddBindingModel() {
    }

    @NotNull
    @NotEmpty(message = Constants.EMPTY_FIELD)
    @NotBlank(message = Constants.EMPTY_FIELD)
    @Size(min = 5, max = 30, message = "Invalid username!")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    @NotNull
    @NotEmpty(message = Constants.EMPTY_FIELD)
    @NotBlank(message = Constants.EMPTY_FIELD)
    @Size(min = 2, max = 20, message = "Invalid department!")
    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    @NotNull
    @NotEmpty(message = Constants.EMPTY_FIELD)
    @NotBlank(message = Constants.EMPTY_FIELD)
    @Size(min = 5, max = 30, message = "Invalid position!")
    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
