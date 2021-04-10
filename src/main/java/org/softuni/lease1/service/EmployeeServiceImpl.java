package org.softuni.lease1.service;

import org.modelmapper.ModelMapper;
import org.softuni.lease1.domain.entity.Employee;
import org.softuni.lease1.domain.model.service.EmployeeServiceModel;
import org.softuni.lease1.domain.model.service.UserServiceModel;
import org.softuni.lease1.error.EmployeeNotFoundException;
import org.softuni.lease1.error.OfferNotFoundException;
import org.softuni.lease1.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, UserService userService, ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @Override
    public void add(EmployeeServiceModel employeeServiceModel, String username) {
        UserServiceModel userServiceModel = userService.findByUsername(username);
        employeeServiceModel.setUser(userServiceModel);
        Employee employee = this.modelMapper.map(employeeServiceModel, Employee.class);
        this.employeeRepository.saveAndFlush(employee);
    }

    @Override
    public List<EmployeeServiceModel> findAllEmployees() {
        return this.employeeRepository.findAll().stream()
                .map(e->this.modelMapper.map(e, EmployeeServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeServiceModel findByUsername(String username) {
        Employee employee = this.employeeRepository.findByUser_Username(username)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee with this id was not found!"));
        return this.modelMapper.map(employee, EmployeeServiceModel.class);
    }
}
