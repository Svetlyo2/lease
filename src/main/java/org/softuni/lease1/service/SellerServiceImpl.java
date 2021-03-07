package org.softuni.lease1.service;

import org.modelmapper.ModelMapper;
import org.softuni.lease1.domain.entity.Car;
import org.softuni.lease1.domain.entity.Seller;
import org.softuni.lease1.domain.model.binding.SellerAddBindingModel;
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

    public SellerRepository getSellerRepository() {
        return sellerRepository;
    }

    @Override
    public void add(SellerAddBindingModel sellerAddBindingModel, String offerId) {
        Seller seller = this.sellerRepository.findByUIC(sellerAddBindingModel.getUIC().trim()).orElse(null);
            Car car = this.offerService.findOfferById(offerId).getCar();
        if (seller != null){
            seller.getCars().add(car);
            Seller newSeller = this.sellerRepository.saveAndFlush(seller);
            this.carService.setSeller(this.modelMapper.map(newSeller, SellerServiceModel.class), car.getId());

        } else {
            sellerAddBindingModel.getCars().add(car);
            Seller newSeller = this.sellerRepository.saveAndFlush(this.modelMapper.map(sellerAddBindingModel, Seller.class));
            this.carService.setSeller(this.modelMapper.map(newSeller, SellerServiceModel.class), car.getId());
        }
    }
}
