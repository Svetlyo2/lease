package org.softuni.lease1.domain.model.service;


import org.softuni.lease1.domain.entity.User;

public class EmployeeServiceModel extends BaseServiceModel{
    private String fullName;
    private String department;
    private String position;
    private UserServiceModel user;

    public EmployeeServiceModel() {
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

//    public User getUser() {
//        return user;
//    }
//
//    public void setUser(User user) {
//        this.user = user;
//    }

        public UserServiceModel getUser() {
        return user;
    }

    public void setUser(UserServiceModel user) {
        this.user = user;
    }
}
