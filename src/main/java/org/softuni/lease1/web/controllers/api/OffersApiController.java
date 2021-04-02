package org.softuni.lease1.web.controllers.api;

import org.softuni.lease1.service.OfferService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class OffersApiController {
    private final OfferService offerService;

    public OffersApiController(OfferService offerService) {
        this.offerService = offerService;
    }


    @GetMapping("/api/offers")
    public Map<String, Integer> getLastOffers(){
        return this.offerService.getLastWeekOffers();
    }
}
