package org.softuni.lease1.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.softuni.lease1.domain.entity.Employee;
import org.softuni.lease1.domain.entity.Type;
import org.softuni.lease1.domain.entity.User;
import org.softuni.lease1.domain.entity.UserProfile;
import org.softuni.lease1.domain.model.service.EmployeeServiceModel;
import org.softuni.lease1.domain.model.service.ProfileServiceModel;
import org.softuni.lease1.error.EmployeeNotFoundException;
import org.softuni.lease1.repository.EmployeeRepository;
import org.softuni.lease1.repository.UserProfileRepository;
import org.softuni.lease1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class EmployeeServiceTests {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private UserRepository userRepository;
    private ModelMapper modelMapper;
    private UserService userService;
    private User user;
    private Employee employee;

    @Before
    public void init() {
        this.modelMapper = new ModelMapper();
        user = new User();
        user.setUsername("kiko");
        user.setPassword("123");
        user.setEmail("k@abv.bg");
        user = this.userRepository.saveAndFlush(user);
        employee = new Employee();
        employee.setFullName("Kiril Kirov");
        employee.setDepartment("Sales");
        employee.setPosition("Administrator");
        employee.setUser(user);
    }

    @Test
    public void employeeService_findAll_ReturnsCorrect() {
        EmployeeService employeeService = new EmployeeServiceImpl(this.employeeRepository, this.userService, this.modelMapper);

        employee = this.employeeRepository.saveAndFlush(employee);

        List<EmployeeServiceModel> employees = employeeService.findAllEmployees();
        Assert.assertEquals(1, employees.size());
        Assert.assertEquals(employee.getFullName(), employees.get(0).getFullName());
        Assert.assertEquals(employee.getDepartment(), employees.get(0).getDepartment());
        Assert.assertEquals(employee.getPosition(), employees.get(0).getPosition());
    }

    @Test
    public void employeeService_findByUsername_ReturnsCorrect() {
        EmployeeService employeeService = new EmployeeServiceImpl(this.employeeRepository, this.userService, this.modelMapper);

        this.employeeRepository.saveAndFlush(employee);
        EmployeeServiceModel actual = employeeService.findByUsername("kiko");
        Assert.assertEquals(employee.getFullName(), actual.getFullName());
    }

    @Test(expected = EmployeeNotFoundException.class)
    public void employeeService_findByUsername_Throws() {
        EmployeeService employeeService = new EmployeeServiceImpl(this.employeeRepository, this.userService, this.modelMapper);

        this.employeeRepository.saveAndFlush(employee);
        EmployeeServiceModel actual = employeeService.findByUsername("pepi");
    }


}
