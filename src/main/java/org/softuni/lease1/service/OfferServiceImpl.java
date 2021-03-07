package org.softuni.lease1.service;

import org.modelmapper.ModelMapper;
import org.softuni.lease1.domain.entity.Offer;
import org.softuni.lease1.domain.model.service.OfferServiceModel;
import org.softuni.lease1.repository.OfferRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class OfferServiceImpl implements OfferService {
    private final OfferRepository offerRepository;
    private final CarService carService;
    private final ModelMapper modelMapper;

    public OfferServiceImpl(OfferRepository offerRepository, CarService carService, ModelMapper modelMapper) {
        this.offerRepository = offerRepository;
        this.carService = carService;
        this.modelMapper = modelMapper;
    }

    @Override
    public void add(OfferServiceModel offerServiceModel, String carId) {
        offerServiceModel.setCar(this.carService.findCarById(carId));
        offerServiceModel.setRequestDate(LocalDateTime.now());
        offerServiceModel.setStatus("REQUESTED");
//        System.out.println();
        String offerId = this.offerRepository.saveAndFlush(this.modelMapper.map(offerServiceModel, Offer.class)).getId();
        this.carService.addOffer(this.modelMapper.map(findOfferById(offerId), OfferServiceModel.class), carId);
    }

    @Override
    public Offer findOfferById(String id) {
        return  this.offerRepository.findById(id).orElse(null);
    }
}
