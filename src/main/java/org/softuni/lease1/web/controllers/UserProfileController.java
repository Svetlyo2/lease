package org.softuni.lease1.web.controllers;

import org.modelmapper.ModelMapper;
import org.softuni.lease1.domain.entity.UserProfile;
import org.softuni.lease1.domain.model.binding.ProfileAddBindingModel;
import org.softuni.lease1.domain.model.binding.ProfileEditBindingModel;
import org.softuni.lease1.domain.model.binding.UserEditBindingModel;
import org.softuni.lease1.domain.model.service.ProfileServiceModel;
import org.softuni.lease1.domain.model.service.UserServiceModel;
import org.softuni.lease1.domain.model.view.UserViewModel;
import org.softuni.lease1.service.UserProfileService;
import org.softuni.lease1.web.annotations.PageTitle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/profile")
public class UserProfileController extends BaseController {
    private final UserProfileService userProfileService;
    private final ModelMapper modelMapper;

    @Autowired
    public UserProfileController(UserProfileService userProfileService, ModelMapper modelMapper) {
        this.userProfileService = userProfileService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/add")
    @PageTitle("Add profile")
    public ModelAndView add(
            @ModelAttribute(name = "bindingModel") ProfileAddBindingModel bindingModel,
            ModelAndView modelAndView) {
        modelAndView.addObject("bindingModel", bindingModel);
        return super.view("user/add-profile", modelAndView);
    }

    @PostMapping("/add")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView addConfirm(
            @Valid @ModelAttribute(name = "bindingModel") ProfileAddBindingModel bindingModel,
            BindingResult bindingResult,
            ModelAndView modelAndView,
            Principal principal) {
        if (bindingResult.hasErrors()) {
            modelAndView.addObject("bindingModel", bindingModel);
            return super.view("user/add-profile", modelAndView);
        }
        ProfileServiceModel profileServiceModel = this.modelMapper.map(bindingModel, ProfileServiceModel.class);
        String name = principal.getName();
        this.userProfileService.add(profileServiceModel, name);
        return super.redirect("/car/car/my-cars");
    }
    @GetMapping("/show")
    @PageTitle("Profile")
    public ModelAndView show( ModelAndView modelAndView, Principal principal) {
        ProfileServiceModel profileServiceModel = this.userProfileService.findProfile(principal.getName());
        if (profileServiceModel == null){
            return super.redirect("/profile/add");
        }
        modelAndView.addObject("model", profileServiceModel);
        return super.view("user/profile", modelAndView);
    }

    @GetMapping("/edit")
    @PreAuthorize("isAuthenticated()")
    @PageTitle("Edit profile")
    public ModelAndView edit(ModelAndView modelAndView, Principal principal) {
        modelAndView.addObject("model", this.userProfileService.findProfile(principal.getName()));
        return super.view("user/edit-profile", modelAndView);
    }

    @PostMapping("/edit")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView editConfirm(@ModelAttribute ProfileEditBindingModel profileEditBindingModel,
                                    Principal principal) {
        this.userProfileService.editProfile(this.modelMapper.map(profileEditBindingModel, ProfileServiceModel.class), principal.getName());
        return super.redirect("/profile/show");
    }
}
