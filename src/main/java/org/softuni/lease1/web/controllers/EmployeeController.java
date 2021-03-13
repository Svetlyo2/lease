package org.softuni.lease1.web.controllers;

import org.modelmapper.ModelMapper;
import org.softuni.lease1.domain.model.binding.EmployeeAddBindingModel;
import org.softuni.lease1.domain.model.service.EmployeeServiceModel;
import org.softuni.lease1.domain.model.view.EmployeeViewModel;
import org.softuni.lease1.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/employees")
public class EmployeeController extends BaseController {
    private final EmployeeService employeeService;
    private final ModelMapper modelMapper;

    @Autowired
    public EmployeeController(EmployeeService employeeService, ModelMapper modelMapper) {
        this.employeeService = employeeService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/add")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView add(@ModelAttribute(name = "bindingModel") EmployeeAddBindingModel bindingModel,
                            ModelAndView modelAndView) {
        modelAndView.addObject("bindingModel", bindingModel);
        return super.view("add-employee", modelAndView);
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView addConfirm(
            @Valid @ModelAttribute(name = "bindingModel") EmployeeAddBindingModel bindingModel,
            BindingResult bindingResult,
            ModelAndView modelAndView) {

        if (bindingResult.hasErrors()) {
            modelAndView.addObject("bindingModel", bindingModel);
            return super.view("add-employee", modelAndView);
        }
        EmployeeServiceModel employeeServiceModel = this.modelMapper.map(bindingModel, EmployeeServiceModel.class);
        String username = bindingModel.getUsername();
        this.employeeService.add(employeeServiceModel, username);
        return super.redirect("/employees/all");
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView allUsers(ModelAndView modelAndView) {
        List<EmployeeViewModel> employees = this.employeeService.findAllEmployees()
                .stream()
                .map(e->this.modelMapper.map(e, EmployeeViewModel.class))
                .collect(Collectors.toList());
        modelAndView.addObject("employees", employees);
        return super.view("all-employees", modelAndView);
    }
}
