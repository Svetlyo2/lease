package org.softuni.lease1.web.controllers;

import org.modelmapper.ModelMapper;
import org.softuni.lease1.domain.model.binding.UserEditBindingModel;
import org.softuni.lease1.domain.model.binding.UserRegisterBindingModel;
import org.softuni.lease1.domain.model.service.RoleServiceModel;
import org.softuni.lease1.domain.model.service.UserServiceModel;
import org.softuni.lease1.domain.model.view.UserListViewModel;
import org.softuni.lease1.service.UserService;
import org.softuni.lease1.validation.UserEditValidator;
import org.softuni.lease1.validation.UserRegisterValidator;
import org.softuni.lease1.web.annotations.PageTitle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/users")
public class UserController extends BaseController{
    private final UserService userService;
    private final UserRegisterValidator validator;
    private final UserEditValidator userEditValidator;
    private final ModelMapper modelMapper;

    @Autowired
    public UserController(UserService userService, UserRegisterValidator validator, UserEditValidator userEditValidator, ModelMapper modelMapper) {
        this.userService = userService;
        this.validator = validator;
        this.userEditValidator = userEditValidator;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/register")
    @PreAuthorize("isAnonymous()")
    @PageTitle("Registration")
    public ModelAndView register(ModelAndView modelAndView,
                                 @ModelAttribute(name = "bindingModel")UserRegisterBindingModel bindingModel){
        modelAndView.addObject("bindingModel", bindingModel);
        return super.view("user/register");
    }

    @PostMapping("/register")
    public ModelAndView registerConfirm(ModelAndView modelAndView,
                                        @Valid @ModelAttribute(name = "bindingModel")UserRegisterBindingModel bindingModel,
                                        BindingResult bindingResult){
        this.validator.validate(bindingModel, bindingResult);
        if (bindingResult.hasErrors()){
            bindingModel.setPassword(null);
            bindingModel.setConfirmPassword(null);
            modelAndView.addObject("bindingModel", bindingModel);
            return super.view("user/register", modelAndView);
        }
        this.userService.registerUser(this.modelMapper.map(bindingModel, UserServiceModel.class));
        return super.redirect("/users/login");
    }

    @GetMapping("/login")
    @PreAuthorize("isAnonymous()")
    @PageTitle("Login")
    public ModelAndView login() {
        return super.view("user/login");
    }

    @GetMapping("/edit")
    @PreAuthorize("isAuthenticated()")
    @PageTitle("Edit user")
    public ModelAndView edit(Principal principal,
                             ModelAndView modelAndView,
                             @ModelAttribute(name = "model") UserEditBindingModel model){
        model = this.modelMapper.map(this.userService.findByUsername(principal.getName()),UserEditBindingModel.class);
        model.setPassword(null);
        modelAndView.addObject("model", model);
        return super.view("user/edit-user", modelAndView);
    }

    @PostMapping("/edit")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView editConfirm(ModelAndView modelAndView,
                                    @Valid @ModelAttribute(name = "model") UserEditBindingModel model,
                                    BindingResult bindingResult){
       this.userEditValidator.validate(model, bindingResult);
        if (bindingResult.hasErrors()) {
            model.setOldPassword(null);
            model.setPassword(null);
            model.setConfirmPassword(null);
            modelAndView.addObject("model", model);

            return super.view("user/edit-user", modelAndView);
        }

        this.userService
                .editUser(this.modelMapper.map(model, UserServiceModel.class), model.getOldPassword());
        return super.redirect("/profile/show");
    }
    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PageTitle("Users")
    public ModelAndView allUsers(ModelAndView modelAndView){
        List<UserListViewModel> users = this.userService.findAllUsers()
                .stream()
                .map(u->{
                    UserListViewModel user = this.modelMapper.map(u, UserListViewModel.class);
                    Set<String> authorities = u.getAuthorities().stream().map(RoleServiceModel::getAuthority).collect(Collectors.toSet());
                    user.setAuthorities(authorities);
                    return user;
                })
                .collect(Collectors.toList());
        modelAndView.addObject("users", users);
        return super.view("user/all-users", modelAndView);
    }

    @PostMapping("/set-user/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView setUser(@PathVariable String id){
        this.userService.setUserRole(id, "user");
        return super.redirect("/users/all");
    }
    @PostMapping("/set-moderator/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView setModerator(@PathVariable String id){
        this.userService.setUserRole(id, "moderator");
        return super.redirect("/users/all");
    }

    @PostMapping("/set-admin/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView setAdmin(@PathVariable String id) {
        this.userService.setUserRole(id, "admin");
        return super.redirect("/users/all");
    }
}
