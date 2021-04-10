package org.softuni.lease1.service;

import org.modelmapper.ModelMapper;
import org.softuni.lease1.domain.entity.Car;
import org.softuni.lease1.domain.entity.Seller;
import org.softuni.lease1.domain.model.binding.SellerAddBindingModel;
import org.softuni.lease1.domain.model.service.CarServiceModel;
import org.softuni.lease1.domain.model.service.SellerServiceModel;
import org.softuni.lease1.repository.SellerRepository;
import org.springframework.stereotype.Service;

@Service
public class SellerServiceImpl implements SellerService {
    private final SellerRepository sellerRepository;
    private final OfferService offerService;
    private final CarService carService;
    private final ModelMapper modelMapper;


    public SellerServiceImpl(SellerRepository sellerRepository, OfferService offerService, CarService carService, ModelMapper modelMapper) {
        this.sellerRepository = sellerRepository;
        this.offerService = offerService;
        this.carService = carService;
        this.modelMapper = modelMapper;
    }

    @Override
    public SellerServiceModel add(SellerAddBindingModel sellerAddBindingModel, String offerId) {
        Seller seller = this.sellerRepository.findByUIC(sellerAddBindingModel.getUIC().trim()).orElse(null);
            CarServiceModel car = this.offerService.findOfferById(offerId).getCar();
        if (seller != null){
            seller.getCars().add(this.modelMapper.map(car, Car.class));
            Seller newSeller = this.sellerRepository.saveAndFlush(seller);
            this.carService.setSeller(this.modelMapper.map(newSeller, SellerServiceModel.class), car.getId());
            return this.modelMapper.map(newSeller, SellerServiceModel.class);

        } else {
            sellerAddBindingModel.getCars().add(this.modelMapper.map(car, Car.class));
            Seller newSeller = this.sellerRepository.saveAndFlush(this.modelMapper.map(sellerAddBindingModel, Seller.class));
            this.carService.setSeller(this.modelMapper.map(newSeller, SellerServiceModel.class), car.getId());
            return this.modelMapper.map(newSeller, SellerServiceModel.class);
        }
    }
}
