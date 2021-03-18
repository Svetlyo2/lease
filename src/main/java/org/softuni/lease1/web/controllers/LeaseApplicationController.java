package org.softuni.lease1.web.controllers;

import org.modelmapper.ModelMapper;
import org.softuni.lease1.domain.model.binding.LeaseApplicationReviewBindingModel;
import org.softuni.lease1.domain.model.service.*;
import org.softuni.lease1.domain.model.view.LeaseApplicationListViewModel;
import org.softuni.lease1.service.LeaseApplicationService;
import org.softuni.lease1.service.OfferService;
import org.softuni.lease1.service.SellerService;
import org.softuni.lease1.service.UserProfileService;
import org.softuni.lease1.web.annotations.PageTitle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/applications")
public class LeaseApplicationController extends BaseController{
    private final LeaseApplicationService leaseApplicationService;
    private final OfferService offerService;
    private final SellerService sellerService;
    private final UserProfileService userProfileService;
    private final ModelMapper modelMapper;

    @Autowired
    public LeaseApplicationController(LeaseApplicationService leaseApplicationService, OfferService offerService, SellerService sellerService, UserProfileService userProfileService, ModelMapper modelMapper) {
        this.leaseApplicationService = leaseApplicationService;
        this.offerService = offerService;
        this.sellerService = sellerService;
        this.userProfileService = userProfileService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/new")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    @PageTitle("New applications")
    public ModelAndView allNewApplications(ModelAndView modelAndView) {
        List<LeaseApplicationListViewModel> applications = this.leaseApplicationService.findAllNewApplications()
                .stream()
                .map(a->{
                    LeaseApplicationListViewModel app = this.modelMapper.map(a, LeaseApplicationListViewModel.class);
                    String fullName = this.userProfileService.findProfile(a.getUser().getUsername()).getFullName();
                    app.setFullName(fullName);
                    app.setMake(a.getOffer().getCar().getMake());
                    app.setPrice(a.getOffer().getCar().getPrice());
                    return app;
                })
                .collect(Collectors.toList());
        modelAndView.addObject("applications",applications);
        return super.view( "new-applications", modelAndView);
    }
    @GetMapping("/edit/{id}")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    @PageTitle("Review application")
    public ModelAndView reviewApplication(@PathVariable("id")String id, ModelAndView modelAndView){

        LeaseApplicationServiceModel application = this.leaseApplicationService.findApplicationById(id);
        ProfileServiceModel profile = this.userProfileService.findProfile(application.getUser().getUsername());
        OfferServiceModel offer = application.getOffer();
        CarServiceModel car = offer.getCar();
        SellerServiceModel seller = car.getSeller();
        modelAndView.addObject("app", application);
        modelAndView.addObject("profile", profile);
        modelAndView.addObject("car", car);
        modelAndView.addObject("seller", seller);
        modelAndView.addObject("offer", offer);
        return super.view("review-application", modelAndView);
    }

    @PostMapping("/edit/{id}")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView reviewApplicationConfirm(@PathVariable("id")String id,
                                                 @ModelAttribute LeaseApplicationReviewBindingModel bindingModel,
                                                 Principal principal){
        LeaseApplicationServiceModel model = this.modelMapper.map(bindingModel, LeaseApplicationServiceModel.class);
        this.leaseApplicationService.reviewApplication(id, model, principal.getName() );
    return super.redirect("/applications/new");
    }
}
