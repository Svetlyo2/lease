package org.softuni.lease1.service;

import org.softuni.lease1.domain.model.service.OfferServiceModel;

import java.util.List;

public interface OfferService {
    void add(OfferServiceModel offerServiceModel, String carId);
    OfferServiceModel findOfferById(String id);
    List<OfferServiceModel> findAllRequestedOffers();
    OfferServiceModel reviewOffer(String id,OfferServiceModel offerServiceModel);
}
