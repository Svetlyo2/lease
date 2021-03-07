package org.softuni.lease1.service;

import org.softuni.lease1.domain.entity.Offer;
import org.softuni.lease1.domain.model.service.OfferServiceModel;

public interface OfferService {
    void add(OfferServiceModel offerServiceModel, String carId);
    Offer findOfferById(String id);
}
