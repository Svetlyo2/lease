package org.softuni.lease1.web.controllers;

import org.modelmapper.ModelMapper;
import org.softuni.lease1.domain.model.binding.OfferAddBindingModel;
import org.softuni.lease1.domain.model.binding.OfferReviewBindingModel;
import org.softuni.lease1.domain.model.service.CarServiceModel;
import org.softuni.lease1.domain.model.service.OfferServiceModel;
import org.softuni.lease1.domain.model.service.ProfileServiceModel;
import org.softuni.lease1.domain.model.view.OfferListViewModel;
import org.softuni.lease1.domain.model.view.OfferRequestViewModel;
import org.softuni.lease1.domain.model.view.ProfileViewModel;
import org.softuni.lease1.service.CarService;
import org.softuni.lease1.service.OfferService;
import org.softuni.lease1.service.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/offers")
public class OfferController extends BaseController{
    private final CarService carService;
    private final OfferService offerService;
    private final UserProfileService userProfileService;
    private final ModelMapper modelMapper;


    @Autowired
    public OfferController(CarService carService, OfferService offerService, UserProfileService userProfileService, ModelMapper modelMapper) {
        this.carService = carService;
        this.offerService = offerService;
        this.userProfileService = userProfileService;
        this.modelMapper = modelMapper;
    }
    @GetMapping("/{id}")
    public ModelAndView carOffers(@PathVariable("id")String id, ModelAndView modelAndView){
        CarServiceModel currentCar = this.carService.findCarById(id);
        modelAndView.addObject("car",currentCar);
        modelAndView.addObject("offers",currentCar.getOffers());
        return super.view("car-offers", modelAndView);
    }
    @GetMapping("/add/{id}")
    public ModelAndView add(@PathVariable("id")String id,
                            @ModelAttribute("bindingModel") OfferAddBindingModel bindingModel,
                            ModelAndView modelAndView){
        modelAndView.addObject("bindingModel", bindingModel);
        return super.view("add-offer", modelAndView);

    }

    @PostMapping("/add/{id}")
    public ModelAndView addConfirm(
            @PathVariable("id")String id,
            @Valid @ModelAttribute("bindingModel") OfferAddBindingModel bindingModel,
            BindingResult bindingResult,
            ModelAndView modelAndView
    ){
        if (bindingResult.hasErrors()){
            modelAndView.addObject("bindingModel", bindingModel);
            return super.view("add-offer", modelAndView);
        }
        OfferServiceModel offerServiceModel = this.modelMapper.map(bindingModel,OfferServiceModel.class);
        offerServiceModel.setStatus("REQUESTED");
        this.offerService.add(offerServiceModel, id );
        return super.redirect("/offers/"+ id);
    }

    @GetMapping("/requested")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView allRequests(ModelAndView modelAndView) {
        List<OfferRequestViewModel> requests = this.offerService.findAllRequestedOffers()
                .stream()
                .map(r->{
                    OfferRequestViewModel request = this.modelMapper.map(r, OfferRequestViewModel.class);
                    request.setMake(r.getCar().getMake());
                    request.setPrice(r.getCar().getPrice());
                    request.setCarId(r.getCar().getId());
                    return request;
                })
                .collect(Collectors.toList());
        modelAndView.addObject("offers", requests);
        return super.view("offers-requested", modelAndView);
    }

    @GetMapping("/edit/{id}")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView reviewOffer(@PathVariable("id")String id, ModelAndView modelAndView){
        OfferServiceModel offer = this.offerService.findOfferById(id);
        ProfileServiceModel profile = this.userProfileService.findProfile(offer.getCar().getUser().getUsername());
        modelAndView.addObject("profile", this.modelMapper.map(profile, ProfileViewModel.class));
        modelAndView.addObject("bindingModel", this.modelMapper.map(offer, OfferListViewModel.class));
        return super.view("review-offer", modelAndView);
    }

    @PostMapping("/edit/{id}")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView reviewOfferConfirm(@PathVariable("id")String id,
                                           @ModelAttribute OfferReviewBindingModel offerReviewBindingModel){
        String carId = this.offerService.findOfferById(id).getCar().getId();
        this.offerService.reviewOffer(id, this.modelMapper.map(offerReviewBindingModel, OfferServiceModel.class));
        return super.redirect("/offers/"+carId);
    }
}
