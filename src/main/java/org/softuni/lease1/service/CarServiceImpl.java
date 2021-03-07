package org.softuni.lease1.service;

import org.modelmapper.ModelMapper;
import org.softuni.lease1.domain.entity.Car;
import org.softuni.lease1.domain.entity.Offer;
import org.softuni.lease1.domain.entity.Seller;
import org.softuni.lease1.domain.model.service.CarServiceModel;
import org.softuni.lease1.domain.model.service.OfferServiceModel;
import org.softuni.lease1.domain.model.service.SellerServiceModel;
import org.softuni.lease1.domain.model.service.UserServiceModel;
import org.softuni.lease1.repository.CarRepository;
import org.softuni.lease1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarServiceImpl implements CarService{
    private final CarRepository carRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public CarServiceImpl(CarRepository carRepository, UserRepository userRepository, UserService userService, ModelMapper modelMapper) {
        this.carRepository = carRepository;
        this.userRepository = userRepository;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @Override
    public CarServiceModel add(CarServiceModel carServiceModel, String name) {
        UserServiceModel currentUser = userService.findByUsername(name);
        carServiceModel.setUser(currentUser);
        Car currentCar = this.modelMapper.map(carServiceModel, Car.class);
//        System.out.println();
        this.carRepository.saveAndFlush(currentCar);
        return this.modelMapper.map(currentCar, CarServiceModel.class);
    }

    @Override
    public List<CarServiceModel> findCarsByUser(String name) {
        UserServiceModel user = userService.findByUsername(name);
        List<CarServiceModel> carList = this.carRepository.findAllByUser_Username(name).orElseThrow(null)
                .stream()
                .map(c ->this.modelMapper.map(c,CarServiceModel.class))
                .collect(Collectors.toList());
        System.out.println(carList.size());
        return carList;
    }

    @Override
    public CarServiceModel findCarById(String id) {
        Car car = this.carRepository.findById(id).orElse(null);
        return this.modelMapper.map(car, CarServiceModel.class);
    }

    @Override
    public void setSeller(SellerServiceModel seller, String carId) {
        CarServiceModel car = findCarById(carId);
        car.setSeller(seller);
        System.out.println();
        this.carRepository.saveAndFlush(this.modelMapper.map(car, Car.class));
    }

    @Override
    public void addOffer(OfferServiceModel offerServiceModel, String id) {
        Car car = this.carRepository.findById(id).orElse(null);
        Offer offer = this.modelMapper.map(offerServiceModel, Offer.class);
        car.getOffers().add(offer);
        this.carRepository.saveAndFlush(car);

    }
}
