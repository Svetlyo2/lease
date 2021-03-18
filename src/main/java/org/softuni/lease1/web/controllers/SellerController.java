package org.softuni.lease1.web.controllers;

import org.modelmapper.ModelMapper;
import org.softuni.lease1.domain.model.binding.SellerAddBindingModel;
import org.softuni.lease1.domain.model.service.SellerServiceModel;
import org.softuni.lease1.service.LeaseApplicationService;
import org.softuni.lease1.service.SellerService;
import org.softuni.lease1.web.annotations.PageTitle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/seller")
public class SellerController extends BaseController{
    private final SellerService sellerService;
    private final LeaseApplicationService leaseApplicationService;
    private final ModelMapper modelMapper;

    @Autowired
    public SellerController(SellerService sellerService, LeaseApplicationService leaseApplicationService, ModelMapper modelMapper) {
        this.sellerService = sellerService;
        this.leaseApplicationService = leaseApplicationService;
        this.modelMapper = modelMapper;
    }
    @GetMapping("/add/{id}")
    @PageTitle("Add seller")
    public ModelAndView add(@PathVariable("id")String id,
                            @ModelAttribute("bindingModel") SellerAddBindingModel bindingModel,
                            ModelAndView modelAndView){
        modelAndView.addObject("bindingModel", bindingModel);
        return super.view("add-seller", modelAndView);
    }

    @PostMapping("/add/{id}")
    public ModelAndView addConfirm(
            @PathVariable("id")String id,
            @Valid @ModelAttribute("bindingModel") SellerAddBindingModel bindingModel,
            BindingResult bindingResult,
            ModelAndView modelAndView,
            Principal principal
    ){
        if (bindingResult.hasErrors()){
            modelAndView.addObject("bindingModel", bindingModel);
            return super.view("add-seller", modelAndView);
        }
        this.sellerService.add(this.modelMapper.map(bindingModel, SellerAddBindingModel.class), id);
        this.leaseApplicationService.add(id, principal.getName());
        return super.view("home");
    }



}
