package org.softuni.lease1.service;

import org.modelmapper.ModelMapper;
import org.softuni.lease1.common.Constants;
import org.softuni.lease1.domain.entity.Offer;
import org.softuni.lease1.domain.model.service.OfferServiceModel;
import org.softuni.lease1.repository.OfferRepository;
import org.softuni.lease1.web.controllers.api.OfferListResponseModel;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OfferServiceImpl implements OfferService {
    private final OfferRepository offerRepository;
    private final CarService carService;
    private LeaseApplicationService leaseApplicationService;
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
    public List<OfferServiceModel> findAllOffers() {
        return this.offerRepository.findAll()
                .stream()
                .map(offer -> this.modelMapper.map(offer, OfferServiceModel.class))
                .collect(Collectors.toList());
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
    public Map<String, Integer> getLastWeekOffers() {
        LocalTime midnight = LocalTime.MIDNIGHT;
        LocalDate today = LocalDate.now();
        LocalDateTime todayMidnight = LocalDateTime.of(today, midnight);
        LocalDateTime startDate = todayMidnight.minusDays(6);
//        return this.offerRepository.findAllByRequestDateAfter(startDate)
//                .stream()
//                .map(o->{
//                    OfferListResponseModel offer = this.modelMapper.map(o, OfferListResponseModel.class);
//                    return offer;
//                }).collect(Collectors.toList());
//        LocalDate start = today.minusDays(7);

        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yy");
        Map<String, Integer> offersCount = new LinkedHashMap<>();
        for (int i = 0; i < 7; i++) {
            offersCount.put(startDate.plusDays(i).format(format), 0);
        }
        this.offerRepository.findAllByRequestDateAfter(startDate)
                .stream()
                .forEach(o->{
                    String offerDate = o.getRequestDate().format(format);
                    offersCount.put(offerDate, offersCount.get(offerDate)+1);
                });
        return offersCount;
    }

    @Override
    public Integer countOverdueRequest() {
        LocalDateTime time = LocalDateTime.now();
        Integer count = (int) this.offerRepository.findAllByStatusIsContaining("REQUESTED")
                .stream()
                .filter(o -> {
                    long duration = Duration.between(o.getRequestDate(), time).toMinutes();
                    return duration > Constants.REQUEST_OVERDUE_TIME;
                }).count();
        return count;
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

    @Override
    public OfferServiceModel changeOfferStatus(String offerId,String status) {
        Offer offer = this.offerRepository.findById(offerId)
                .orElseThrow(()-> new IllegalArgumentException("Offer not found!"));
        offer.setStatus(status);
        return this.modelMapper.map(this.offerRepository.saveAndFlush(offer), OfferServiceModel.class);
    }

    @Override
    public void deleteOffer(String id) {
        Offer offer = this.offerRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("Offer not found!"));
        this.offerRepository.delete(offer);
    }

    @Scheduled(fixedRate = 300000)
    private void generateReminder(){
        int count = this.countOverdueRequest();
        if (count > 0){
        System.out.println(this.countOverdueRequest() + " overdue requests");
        }
    }
}
