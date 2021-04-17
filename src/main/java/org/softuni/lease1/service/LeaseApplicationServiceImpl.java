package org.softuni.lease1.service;

import org.modelmapper.ModelMapper;
import org.softuni.lease1.domain.entity.AppStatus;
import org.softuni.lease1.domain.entity.Employee;
import org.softuni.lease1.domain.entity.LeaseApplication;
import org.softuni.lease1.domain.entity.User;
import org.softuni.lease1.domain.model.binding.LeaseApplicationAddModel;
import org.softuni.lease1.domain.model.service.EmployeeServiceModel;
import org.softuni.lease1.domain.model.service.LeaseApplicationServiceModel;
import org.softuni.lease1.domain.model.service.OfferServiceModel;
import org.softuni.lease1.error.ApplicationNotFoundException;
import org.softuni.lease1.error.OfferNotFoundException;
import org.softuni.lease1.repository.LeaseApplicationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LeaseApplicationServiceImpl implements LeaseApplicationService {
    private final LeaseApplicationRepository leaseApplicationRepository;
    private final UserService userService;
    private final OfferService offerService;
    private final EmployeeService employeeService;
    private final ModelMapper modelMapper;


    public LeaseApplicationServiceImpl(LeaseApplicationRepository leaseApplicationRepository, UserService userService, OfferService offerService, EmployeeService employeeService, ModelMapper modelMapper) {
        this.leaseApplicationRepository = leaseApplicationRepository;
        this.userService = userService;
        this.offerService = offerService;
        this.employeeService = employeeService;
        this.modelMapper = modelMapper;
    }


    @Override
    public LeaseApplicationServiceModel add(String offerId, String username) {
    LeaseApplicationAddModel leaseApplicationAddModel = new LeaseApplicationAddModel();
    OfferServiceModel offer = this.offerService.changeOfferStatus(offerId, "APPLIED");
    leaseApplicationAddModel.setOffer(offer);
    leaseApplicationAddModel.setAppStatus("RECEIVED");
    leaseApplicationAddModel.setRequestDate(LocalDateTime.now());
    leaseApplicationAddModel.setUser(this.modelMapper.map(this.userService.findByUsername(username), User.class));
    LeaseApplication application = this.leaseApplicationRepository.saveAndFlush(this.modelMapper.map(leaseApplicationAddModel, LeaseApplication.class));
    return this.modelMapper.map(application, LeaseApplicationServiceModel.class);
    }

    @Override
    public List<LeaseApplicationServiceModel> findApplicationsByUser(String username) {
        List<LeaseApplicationServiceModel> applications = this.leaseApplicationRepository.findAllByUser_Username(username)
                .stream()
                .map(a->this.modelMapper.map(a,LeaseApplicationServiceModel.class))
                .collect(Collectors.toList());
        return applications;
    }

    @Override
    public List<LeaseApplicationServiceModel> findAllNewApplications() {
        List<LeaseApplicationServiceModel> applications = this.leaseApplicationRepository
                .findAllByAppStatus(AppStatus.RECEIVED)
                .stream()
                .map(a->this.modelMapper.map(a, LeaseApplicationServiceModel.class))
                .sorted((a1, a2) -> a2.getRequestDate().compareTo(a1.getRequestDate()))
                .collect(Collectors.toList());
        return applications;
    }

    @Override
    public LeaseApplicationServiceModel findApplicationById(String id) {
        LeaseApplication app = this.leaseApplicationRepository.findById(id)
                .orElseThrow(() -> new ApplicationNotFoundException("Lease application with this id was not found!"));
        return this.modelMapper.map(app, LeaseApplicationServiceModel.class);
    }

    @Override
    public LeaseApplicationServiceModel reviewApplication(String id, LeaseApplicationServiceModel model, String username) {
        LeaseApplication leaseApplication = this.leaseApplicationRepository.findById(id)
                .orElseThrow(() -> new ApplicationNotFoundException("Lease application with this id was not found!"));
        EmployeeServiceModel employee = this.employeeService.findByUsername(username);
        leaseApplication.setDecisionDate(LocalDateTime.now());
        leaseApplication.setDescription(model.getDescription());
        leaseApplication.setAppStatus(AppStatus.valueOf(model.getAppStatus()));
        leaseApplication.setEmployee(this.modelMapper.map(employee, Employee.class));
        return this.modelMapper.map(this.leaseApplicationRepository.saveAndFlush(leaseApplication), LeaseApplicationServiceModel.class);
    }

    @Override
    public LeaseApplicationServiceModel findApplicationByOfferId(String id) {
        return this.leaseApplicationRepository.findByOffer_Id(id).orElse(null);
    }
}
