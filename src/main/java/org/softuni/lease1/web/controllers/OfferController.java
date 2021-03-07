package org.softuni.lease1.web.controllers;

import org.modelmapper.ModelMapper;
import org.softuni.lease1.domain.entity.Car;
import org.softuni.lease1.domain.model.binding.OfferAddBindingModel;
import org.softuni.lease1.domain.model.service.CarServiceModel;
import org.softuni.lease1.domain.model.service.OfferServiceModel;
import org.softuni.lease1.domain.model.view.CarListViewModel;
import org.softuni.lease1.service.CarService;
import org.softuni.lease1.service.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Set;

@Controller
@RequestMapping("/offers")
public class OfferController extends BaseController{
    private final CarService carService;
    private final OfferService offerService;
    private final ModelMapper modelMapper;


    @Autowired
    public OfferController(CarService carService, OfferService offerService, ModelMapper modelMapper) {
        this.carService = carService;
        this.offerService = offerService;
        this.modelMapper = modelMapper;
    }
    @GetMapping("/{id}")
    public ModelAndView offers(@PathVariable("id")String id, ModelAndView modelAndView){
        CarServiceModel currentCar = this.carService.findCarById(id);
        modelAndView.addObject("car",currentCar);
        modelAndView.addObject("offers",currentCar.getOffers());
//        System.out.println();
//        modelAndView.setViewName("car-offers");
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
}
