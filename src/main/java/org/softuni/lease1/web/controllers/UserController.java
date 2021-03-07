package org.softuni.lease1.web.controllers;

import org.modelmapper.ModelMapper;
import org.softuni.lease1.domain.model.binding.UserEditBindingModel;
import org.softuni.lease1.domain.model.binding.UserRegisterBindingModel;
import org.softuni.lease1.domain.model.service.UserServiceModel;
import org.softuni.lease1.domain.model.view.UserListViewModel;
import org.softuni.lease1.domain.model.view.UserViewModel;
import org.softuni.lease1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/users")
public class UserController extends BaseController{
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/register")
    @PreAuthorize("isAnonymous()")
    public ModelAndView register(){
        return super.view("register");
    }

    @PostMapping("/register")
    public ModelAndView registerConfirm(@ModelAttribute("userRegisterBindingModel")UserRegisterBindingModel userRegisterBindingModel){
        if (!userRegisterBindingModel.getPassword().equals(userRegisterBindingModel.getConfirmPassword())){
            return super.view("register");
        }
        this.userService.registerUser(this.modelMapper.map(userRegisterBindingModel, UserServiceModel.class));
        return super.redirect("/login");
    }

    @GetMapping("/login")
    @PreAuthorize("isAnonymous()")
    public ModelAndView login() {
        return super.view("login");
    }

    @GetMapping("/edit")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView edit(Principal principal, ModelAndView modelAndView){
        modelAndView.addObject("model", this.modelMapper.map(this.userService.findByUsername(principal.getName()), UserViewModel.class));
        return super.view("edit-user", modelAndView);
    }

    @PostMapping("/edit")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView editConfirm(@ModelAttribute UserEditBindingModel userEditBindingModel){
        System.out.println();
        if (!userEditBindingModel.getPassword().equals(userEditBindingModel.getConfirmPassword())){
            return super.view("edit-user");
        }
        this.userService
                .editUser(this.modelMapper.map(userEditBindingModel, UserServiceModel.class), userEditBindingModel.getOldPassword());
        return super.redirect("/profile/show");
    }
    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView allUsers(ModelAndView modelAndView){
        List<UserListViewModel> users = this.userService.findAllUsers()
                .stream()
                .map(u->{
                    UserListViewModel user = this.modelMapper.map(u, UserListViewModel.class);
                    user.setAuthorities(u.getAuthorities().stream().map(a->a.getAuthority()).collect(Collectors.toSet()));
                })
                .collect(Collectors.toList());
        modelAndView.addObject("users", users);
        return super.view("all-users", modelAndView);
    }


}
