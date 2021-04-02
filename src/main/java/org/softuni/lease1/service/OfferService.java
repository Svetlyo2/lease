package org.softuni.lease1.service;

import org.softuni.lease1.domain.model.service.OfferServiceModel;
import org.softuni.lease1.web.controllers.api.OfferListResponseModel;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface OfferService {
    void add(OfferServiceModel offerServiceModel, String carId);
    OfferServiceModel findOfferById(String id);
    List<OfferServiceModel> findAllOffers();
    List<OfferServiceModel> findAllRequestedOffers();
    Map<String, Integer> getLastWeekOffers();
    OfferServiceModel reviewOffer(String id,OfferServiceModel offerServiceModel);
    OfferServiceModel changeOfferStatus(String id,String status);
    Integer countOverdueRequest();
    void deleteOffer(String id);
}
