package org.softuni.lease1.service;


import org.softuni.lease1.domain.entity.Car;
import org.softuni.lease1.domain.entity.Seller;
import org.softuni.lease1.domain.model.service.CarServiceModel;
import org.softuni.lease1.domain.model.service.OfferServiceModel;
import org.softuni.lease1.domain.model.service.SellerServiceModel;

import java.util.List;

public interface CarService {
    CarServiceModel add(CarServiceModel carServiceModel, String name);
    List<CarServiceModel> findCarsByUser(String name);
    CarServiceModel findCarById(String id);
    void setSeller (SellerServiceModel seller, String carId);
    void addOffer(OfferServiceModel offerServiceModel, String id);
//    List<CarServiceModel> findAll();
}
