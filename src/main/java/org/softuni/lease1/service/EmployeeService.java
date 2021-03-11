package org.softuni.lease1.service;

import org.softuni.lease1.domain.model.service.EmployeeServiceModel;

import java.util.List;

public interface EmployeeService {
    void add(EmployeeServiceModel employeeServiceModel, String username);
    List<EmployeeServiceModel> findAllEmployees();
    EmployeeServiceModel findByUsername(String username);
}
