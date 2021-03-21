package org.softuni.lease1.web.controllers;

import org.modelmapper.ModelMapper;
import org.softuni.lease1.domain.model.binding.CarAddBindingModel;
import org.softuni.lease1.domain.model.binding.CarShowBindingModel;
import org.softuni.lease1.domain.model.service.CarServiceModel;
import org.softuni.lease1.domain.model.view.CarListViewModel;
import org.softuni.lease1.service.CarService;
import org.softuni.lease1.service.CloudinaryService;
import org.softuni.lease1.web.annotations.PageTitle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/car")
public class CarController extends BaseController{
    private final CarService carService;
    private final CloudinaryService cloudinaryService;
    private final ModelMapper modelMapper;

    @Autowired
    public CarController(CarService carService, CloudinaryService cloudinaryService, ModelMapper modelMapper) {
        this.carService = carService;
        this.cloudinaryService = cloudinaryService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/add")
    @PageTitle("Add car")
    public ModelAndView add(
            @ModelAttribute("bindingModel")CarAddBindingModel bindingModel,
            ModelAndView modelAndView){
        modelAndView.addObject("bindingModel", bindingModel);
        return super.view("car/add-car", modelAndView);
    }

    @PostMapping("/add")
    public ModelAndView addConfirm(
            @Valid @ModelAttribute("bindingModel") CarAddBindingModel bindingModel,
            BindingResult bindingResult,
            ModelAndView modelAndView,
            Principal principal
    ) throws IOException {
        if (bindingResult.hasErrors()){
            modelAndView.addObject("bindingModel", bindingModel);
            return super.view("car/add-car", modelAndView);
        }
        CarServiceModel newCarModel = this.modelMapper.map(bindingModel, CarServiceModel.class);
        if (!bindingModel.getCarOffer().isEmpty()){
            String url = this.cloudinaryService.uploadOffer(bindingModel.getCarOffer());
            if (url.endsWith("pdf")){
                url = url.substring(0, url.length()-3) +"jpg";        }
            newCarModel.setOfferUrl(url);
        }
        this.carService.add(newCarModel, principal.getName());
        return super.redirect("/car/my-cars");
    }
    @GetMapping("/my-cars")
    @PageTitle("My cars")
    public ModelAndView showCars(
            @ModelAttribute CarShowBindingModel bindingModel,
            ModelAndView modelAndView,
            Principal principal){
        List<CarListViewModel> carListViewModels = carService.findCarsByUser(principal.getName())
                .stream()
                .map(c -> modelMapper.map(c, CarListViewModel.class))
                .collect(Collectors.toList());
        modelAndView.setViewName("my-cars");
        modelAndView.addObject("cars", carListViewModels);
        return super.view("car/my-cars", modelAndView);
    }
}
