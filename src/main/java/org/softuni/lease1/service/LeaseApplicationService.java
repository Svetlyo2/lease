package org.softuni.lease1.service;

import org.softuni.lease1.domain.model.binding.LeaseApplicationAddModel;
import org.softuni.lease1.domain.model.service.LeaseApplicationServiceModel;
import org.springframework.ui.Model;

import java.util.List;

public interface LeaseApplicationService {
    void add(String offerId, String username);
    LeaseApplicationServiceModel findApplicationByUser(String username);
    List<LeaseApplicationServiceModel> findAllNewApplications();
}
