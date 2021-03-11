package org.softuni.lease1.service;

import org.modelmapper.ModelMapper;
import org.softuni.lease1.domain.entity.LeaseApplication;
import org.softuni.lease1.domain.entity.UserProfile;
import org.softuni.lease1.domain.model.binding.LeaseApplicationAddModel;
import org.softuni.lease1.domain.model.service.LeaseApplicationServiceModel;
import org.softuni.lease1.repository.LeaseApplicationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class LeaseApplicationServiceImpl implements LeaseApplicationService {
    private final LeaseApplicationRepository leaseApplicationRepository;
    private final UserProfileService userProfileService;
    private final OfferService offerService;
    private final ModelMapper modelMapper;

    public LeaseApplicationServiceImpl(LeaseApplicationRepository leaseApplicationRepository, UserProfileService userProfileService, OfferService offerService, ModelMapper modelMapper) {
        this.leaseApplicationRepository = leaseApplicationRepository;
        this.userProfileService = userProfileService;
        this.offerService = offerService;
        this.modelMapper = modelMapper;
    }


    @Override
    public void add(String offerId, String username) {
    LeaseApplicationAddModel leaseApplicationAddModel = new LeaseApplicationAddModel();
    leaseApplicationAddModel.setAppStatus("RECEIVED");
    leaseApplicationAddModel.setRequestDate(LocalDateTime.now());
    leaseApplicationAddModel.setOffer(this.offerService.findOfferById(offerId));
    leaseApplicationAddModel.setUser(this.userProfileService.findProfile(username));
    this.leaseApplicationRepository.saveAndFlush(this.modelMapper.map(leaseApplicationAddModel, LeaseApplication.class));
    }

    @Override
    public LeaseApplicationServiceModel findApplicationByUser(String username) {
        String userId = this.userProfileService.findProfile(username).getId();
        LeaseApplication leaseApplication = this.leaseApplicationRepository.findByUser_Id(userId).orElse(null);
        return this.modelMapper.map(leaseApplication, LeaseApplicationServiceModel.class);
    }
}
