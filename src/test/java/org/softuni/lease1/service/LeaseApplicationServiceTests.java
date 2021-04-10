package org.softuni.lease1.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.softuni.lease1.domain.entity.*;
import org.softuni.lease1.domain.model.service.LeaseApplicationServiceModel;
import org.softuni.lease1.domain.model.service.OfferServiceModel;
import org.softuni.lease1.error.ApplicationNotFoundException;
import org.softuni.lease1.error.OfferNotFoundException;
import org.softuni.lease1.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class LeaseApplicationServiceTests {
    @Autowired
    private LeaseApplicationRepository leaseApplicationRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SellerRepository sellerRepository;
    @Autowired
    private OfferRepository offerRepository;
    @Autowired
    private CarRepository carRepository;
    private ModelMapper modelMapper;
    private OfferService offerService;
    private UserService userService;
    private EmployeeService employeeService;
    private Car car;
    private User user;
    private Offer offer;
    private LeaseApplication application;

    @Before
    public void init() {
        this.modelMapper = new ModelMapper();
        user = new User();
        user.setUsername("kiko");
        user.setPassword("123");
        user.setEmail("k@abv.bg");
        user = this.userRepository.saveAndFlush(user);
        car = new Car();
        car.setCarCondition(CarCondition.NEW);
        car.setMake("Opel");
        car.setCarModel("Astra");
        car.setYear(2015);
        car.setMileage(55020);
        car.setPrice(new BigDecimal("12000"));
        car.setSalesperson("Misho");
        car.setEmail("m@mail.bg");
        car.setMobile("0222323232");
        car.setUser(user);
        car = this.carRepository.saveAndFlush(car);
        offer = new Offer();
        offer.setDeposit(10);
        offer.setTerm(48);
        offer.setResidualValue(10);
        offer.setInterest(new BigDecimal("3"));
        offer.setFee(new BigDecimal("2"));
        offer.setStatus("REQUESTED");
        offer.setRequestDate(LocalDateTime.now());
        offer.setCar(car);
        offer = this.offerRepository.saveAndFlush(offer);
        application = new LeaseApplication();
        application.setAppStatus(AppStatus.RECEIVED);
        application.setRequestDate(LocalDateTime.now());
        application.setUser(user);
        application.setOffer(offer);
    }
    @Test
    public void applicationService_addApplication_ReturnsCorrect() {
        LeaseApplicationService applicationService = new LeaseApplicationServiceImpl(this.leaseApplicationRepository, this.userService, this.offerService, this.employeeService, this.modelMapper);

//        LeaseApplicationServiceModel actual = applicationService.add(offer.getId(), user.getUsername());
//        Assert.assertEquals(user.getUsername(), actual.getUser().getUsername());
    }

    @Test
    public void applicationService_findByIdWithCorrectValue_ReturnsCorrect() {
        LeaseApplicationService applicationService = new LeaseApplicationServiceImpl(this.leaseApplicationRepository, this.userService, this.offerService, this.employeeService, this.modelMapper);
        LeaseApplication saved = this.leaseApplicationRepository.saveAndFlush(application);
        LeaseApplicationServiceModel actual = applicationService.findApplicationById(saved.getId());
        Assert.assertEquals(saved.getId(), actual.getId());
        Assert.assertEquals(application.getAppStatus().toString(), actual.getAppStatus().toString());
        Assert.assertEquals(application.getRequestDate(), actual.getRequestDate());
        Assert.assertEquals(application.getUser().getId(), actual.getUser().getId());
    }

    @Test(expected = ApplicationNotFoundException.class)
    public void applicationService_findByIdWithWrong_Throws() {
        LeaseApplicationService applicationService = new LeaseApplicationServiceImpl(this.leaseApplicationRepository, this.userService, this.offerService, this.employeeService, this.modelMapper);
        LeaseApplication saved = this.leaseApplicationRepository.saveAndFlush(application);
        LeaseApplicationServiceModel actual = applicationService.findApplicationById("123");
    }

    @Test
    public void applicationService_findByUsernameWithCorrectValue_ReturnsCorrect() {
        LeaseApplicationService applicationService = new LeaseApplicationServiceImpl(this.leaseApplicationRepository, this.userService, this.offerService, this.employeeService, this.modelMapper);
        LeaseApplication saved = this.leaseApplicationRepository.saveAndFlush(application);

        User user2 = new User();
        user2.setUsername("pepi");
        user2.setPassword("123");
        user2.setEmail("pepi@abv.bg");
        user2 = this.userRepository.saveAndFlush(user2);
        LeaseApplication application2 = new LeaseApplication();
        application2.setAppStatus(AppStatus.WAITING);
        application2.setRequestDate(LocalDateTime.now().minusDays(1));
        application2.setUser(user2);
        LeaseApplication saved2 = this.leaseApplicationRepository.saveAndFlush(application2);
        List<LeaseApplicationServiceModel> applications = applicationService.findApplicationsByUser(application2.getUser().getUsername());
        LeaseApplicationServiceModel actual = applications.get(0);
        Assert.assertEquals(saved2.getId(), actual.getId());
        Assert.assertEquals(application2.getAppStatus().toString(), actual.getAppStatus().toString());
        Assert.assertEquals(application2.getRequestDate(), actual.getRequestDate());
        Assert.assertEquals(application2.getUser().getId(), actual.getUser().getId());
    }

    @Test
    public void applicationService_findByUsernameWithWrong_ReturnsEmpty() {
        LeaseApplicationService applicationService = new LeaseApplicationServiceImpl(this.leaseApplicationRepository, this.userService, this.offerService, this.employeeService, this.modelMapper);
        LeaseApplication saved = this.leaseApplicationRepository.saveAndFlush(application);

        List<LeaseApplicationServiceModel> applications = applicationService.findApplicationsByUser("missing");
        Assert.assertEquals(0, applications.size());
    }

    @Test
    public void applicationService_findAllNewApplications_ReturnsCorrect() {
        LeaseApplicationService applicationService = new LeaseApplicationServiceImpl(this.leaseApplicationRepository, this.userService, this.offerService, this.employeeService, this.modelMapper);
        LeaseApplication saved = this.leaseApplicationRepository.saveAndFlush(application);
        LeaseApplication application2 = new LeaseApplication();
        application2.setAppStatus(AppStatus.RECEIVED);
        application2.setRequestDate(LocalDateTime.now().minusDays(1));
        application2.setUser(user);
        LeaseApplication saved2 = this.leaseApplicationRepository.saveAndFlush(application2);
        List<LeaseApplicationServiceModel> applications = applicationService.findAllNewApplications();
//        LeaseApplicationServiceModel actual = applications.get(0);
        Assert.assertEquals(2, applications.size());
//        Assert.assertEquals(application.getRequestDate(), actual.getRequestDate());
    }

    @Test
    public void applicationService_findAllNewApplicationsWithEmpty_ReturnsEmpty() {
        LeaseApplicationService applicationService = new LeaseApplicationServiceImpl(this.leaseApplicationRepository, this.userService, this.offerService, this.employeeService, this.modelMapper);
        List<LeaseApplicationServiceModel> applications = applicationService.findAllNewApplications();
        Assert.assertEquals(0, applications.size());
    }

    @Test
    public void applicationService_findApplicationByOfferId_ReturnsCorrect() {
        LeaseApplicationService applicationService = new LeaseApplicationServiceImpl(this.leaseApplicationRepository, this.userService, this.offerService, this.employeeService, this.modelMapper);
        LeaseApplication saved = this.leaseApplicationRepository.saveAndFlush(application);
        String offerId = saved.getOffer().getId();

        Offer offer2 = new Offer();
        offer2.setDeposit(15);
        offer2.setTerm(36);
        offer2.setResidualValue(0);
        offer2.setInterest(new BigDecimal("3.5"));
        offer2.setFee(new BigDecimal("1.5"));
        offer2.setStatus("APPROVED");
        offer2.setRequestDate(LocalDateTime.now().minusDays(1));
        offer2.setCar(car);
        offer2 = this.offerRepository.saveAndFlush(offer2);
        LeaseApplication application2 = new LeaseApplication();
        application2.setOffer(offer2);
        application2.setAppStatus(AppStatus.RECEIVED);
        application2.setRequestDate(LocalDateTime.now().minusDays(1));
        application2.setUser(user);
        LeaseApplication saved2 = this.leaseApplicationRepository.saveAndFlush(application2);
//        LeaseApplicationServiceModel actual = applicationService.findApplicationByOfferId(offer.getId());
//        String actualOfferId = actual.getOffer().getId();
//        Assert.assertEquals(offerId, actualOfferId);
    }



}
