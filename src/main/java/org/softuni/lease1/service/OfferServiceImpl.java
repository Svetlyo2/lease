package org.softuni.lease1.service;

import org.modelmapper.ModelMapper;
import org.softuni.lease1.domain.entity.Offer;
import org.softuni.lease1.domain.model.service.OfferServiceModel;
import org.softuni.lease1.repository.OfferRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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
        String offerId = this.offerRepository.saveAndFlush(this.modelMapper.map(offerServiceModel, Offer.class)).getId();
        this.carService.addOffer(this.modelMapper.map(findOfferById(offerId), OfferServiceModel.class), carId);
    }

    @Override
    public OfferServiceModel findOfferById(String id) {
        return  this.offerRepository.findById(id)
                .map(o->this.modelMapper.map(o, OfferServiceModel.class))
                .orElseThrow(() -> new IllegalArgumentException("Offer not found!"));
    }

    @Override
    public List<OfferServiceModel> findAllRequestedOffers() {
        List<OfferServiceModel> offers = this.offerRepository.findAllByStatusIsContaining("REQUESTED")
                .stream()
                .map(offer -> this.modelMapper.map(offer, OfferServiceModel.class))
                .collect(Collectors.toList());
        return offers;
    }

    @Override
    public OfferServiceModel reviewOffer(String id,OfferServiceModel offerServiceModel) {
        Offer offer = this.offerRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("Offer not found!"));
        offer.setDeposit(offerServiceModel.getDeposit());
        offer.setTerm(offerServiceModel.getTerm());
        offer.setResidualValue(offerServiceModel.getResidualValue());
        offer.setInterest(offerServiceModel.getInterest());
        offer.setFee(offerServiceModel.getFee());
        offer.setStatus(offerServiceModel.getStatus());
        return this.modelMapper.map(this.offerRepository.saveAndFlush(offer), OfferServiceModel.class);
    }


}
