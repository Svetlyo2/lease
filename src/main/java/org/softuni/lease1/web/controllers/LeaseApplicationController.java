package org.softuni.lease1.web.controllers;

import org.modelmapper.ModelMapper;
import org.softuni.lease1.domain.model.service.CarServiceModel;
import org.softuni.lease1.domain.model.service.OfferServiceModel;
import org.softuni.lease1.domain.model.service.ProfileServiceModel;
import org.softuni.lease1.domain.model.service.SellerServiceModel;
import org.softuni.lease1.domain.model.view.LeaseApplicationListViewModel;
import org.softuni.lease1.domain.model.view.OfferListViewModel;
import org.softuni.lease1.service.LeaseApplicationService;
import org.softuni.lease1.service.OfferService;
import org.softuni.lease1.service.SellerService;
import org.softuni.lease1.service.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
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
    @GetMapping("/{id}")
    public ModelAndView details(@PathVariable("id")String id, Principal principal){
        ProfileServiceModel user = this.userProfileService.findProfile(principal.getName());
        OfferServiceModel offerModel = this.modelMapper.map(this.offerService.findOfferById(id), OfferServiceModel.class);
        OfferListViewModel offer = this.modelMapper.map(offerModel, OfferListViewModel.class);
        CarServiceModel car = this.modelMapper.map(offerModel.getCar(), CarServiceModel.class);
        SellerServiceModel seller = car.getSeller();

        return super.view("application-details");
    }
    @GetMapping("/new")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView allNewApplications(ModelAndView modelAndView) {
        List<LeaseApplicationListViewModel> applications = this.leaseApplicationService.findAllNewApplications()
                .stream()
                .map(a->{
                    LeaseApplicationListViewModel app = this.modelMapper.map(a, LeaseApplicationListViewModel.class);
                    String username = a.getOffer().getCar().getUser().getUsername();
                    app.setFullName(this.userProfileService.findProfile(username).getFullName());
                    app.setMake(a.getOffer().getCar().getMake());
                    app.setPrice(a.getOffer().getCar().getPrice());
                    return app;
                })
                .collect(Collectors.toList());
        modelAndView.addObject("applications",applications);
        return super.view( "new-applications", modelAndView);

    }
}
