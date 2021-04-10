package org.softuni.lease1.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.softuni.lease1.domain.entity.Car;
import org.softuni.lease1.domain.entity.CarCondition;
import org.softuni.lease1.domain.entity.Offer;
import org.softuni.lease1.domain.entity.User;
import org.softuni.lease1.domain.model.service.OfferServiceModel;
import org.softuni.lease1.error.OfferNotFoundException;
import org.softuni.lease1.repository.CarRepository;
import org.softuni.lease1.repository.OfferRepository;
import org.softuni.lease1.repository.SellerRepository;
import org.softuni.lease1.repository.UserRepository;
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
public class OfferServiceTests {
    @Autowired
    private CarRepository carRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SellerRepository sellerRepository;
    @Autowired
    private OfferRepository offerRepository;
    private ModelMapper modelMapper;
    private Car car;
    private CarService carService;
    private User user;
    private Offer offer;

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
    }

    @Test
    public void offerService_findByIdWithCorrectValue_ReturnsCorrect() {
        OfferService offerService = new OfferServiceImpl(this.offerRepository, this.carService, this.modelMapper);
        offer = this.offerRepository.saveAndFlush(offer);
        OfferServiceModel actual = offerService.findOfferById(offer.getId());
        Assert.assertEquals(offer.getId(), actual.getId());
    }

    @Test(expected = OfferNotFoundException.class)
    public void offerService_findByIdWithWrongValue_Throws() {
        OfferService offerService = new OfferServiceImpl(this.offerRepository, this.carService, this.modelMapper);
        offer = this.offerRepository.saveAndFlush(offer);
        OfferServiceModel actual = offerService.findOfferById("123");
    }

    @Test
    public void offerService_findAll_ReturnsCorrect() {
        OfferService offerService = new OfferServiceImpl(this.offerRepository, this.carService, this.modelMapper);
        offer = this.offerRepository.saveAndFlush(offer);
        Offer offer2 = new Offer();
        offer2.setDeposit(10);
        offer2.setTerm(48);
        offer2.setResidualValue(10);
        offer2.setInterest(new BigDecimal("3"));
        offer2.setFee(new BigDecimal("2"));
        offer2.setStatus("PRESENTED");
        offer2.setRequestDate(LocalDateTime.now());
        offer2.setCar(car);
        offer2 = this.offerRepository.saveAndFlush(offer2);
        List<OfferServiceModel> offers = offerService.findAllOffers();
        Assert.assertEquals(2, offers.size());
    }

    @Test
    public void offerService_findAllWithEmpty_ReturnsCorrect() {
        OfferService offerService = new OfferServiceImpl(this.offerRepository, this.carService, this.modelMapper);
        List<OfferServiceModel> offers = offerService.findAllOffers();
        Assert.assertEquals(0, offers.size());
    }

    @Test
    public void offerService_findAllRequested_ReturnsCorrect() {
        OfferService offerService = new OfferServiceImpl(this.offerRepository, this.carService, this.modelMapper);
        offer = this.offerRepository.saveAndFlush(offer);
        Offer offer2 = new Offer();
        offer2.setDeposit(10);
        offer2.setTerm(48);
        offer2.setResidualValue(10);
        offer2.setInterest(new BigDecimal("3"));
        offer2.setFee(new BigDecimal("2"));
        offer2.setStatus("PRESENTED");
        offer2.setRequestDate(LocalDateTime.now());
        offer2.setCar(car);
        offer2 = this.offerRepository.saveAndFlush(offer2);
        List<OfferServiceModel> offers = offerService.findAllRequestedOffers();
        OfferServiceModel actual = offers.get(0);
        Assert.assertEquals(1, offers.size());
        Assert.assertEquals(offer.getId(), actual.getId());
        Assert.assertEquals(offer.getDeposit(), actual.getDeposit());
        Assert.assertEquals(offer.getTerm(), actual.getTerm());
        Assert.assertEquals(offer.getResidualValue(), actual.getResidualValue());
        Assert.assertEquals(offer.getRequestDate(), actual.getRequestDate());
        Assert.assertEquals(offer.getFee(), actual.getFee());
        Assert.assertEquals(offer.getInterest(), actual.getInterest());
        Assert.assertEquals(offer.getStatus(), actual.getStatus());
        Assert.assertEquals(offer.getCar().getId(), actual.getCar().getId());
    }

    @Test
    public void offerService_findAllRequestedWithEmpty_ReturnsEmpty() {
        OfferService offerService = new OfferServiceImpl(this.offerRepository, this.carService, this.modelMapper);
        offer.setStatus("REVISED");
        offer = this.offerRepository.saveAndFlush(offer);
        Offer offer2 = new Offer();
        offer2.setDeposit(10);
        offer2.setTerm(48);
        offer2.setResidualValue(10);
        offer2.setInterest(new BigDecimal("3"));
        offer2.setFee(new BigDecimal("2"));
        offer2.setStatus("PRESENTED");
        offer2.setRequestDate(LocalDateTime.now());
        offer2.setCar(car);
        offer2 = this.offerRepository.saveAndFlush(offer2);
        List<OfferServiceModel> offers = offerService.findAllRequestedOffers();

        Assert.assertEquals(0, offers.size());
    }

    @Test
    public void offerService_getLastWeekOffers_ReturnsCorrect() {
        OfferService offerService = new OfferServiceImpl(this.offerRepository, this.carService, this.modelMapper);
        offer = this.offerRepository.saveAndFlush(offer);
        LocalDateTime oldDate = LocalDateTime.now().minusDays(8);
        Offer offer2 = new Offer();
        offer2.setDeposit(10);
        offer2.setTerm(48);
        offer2.setResidualValue(10);
        offer2.setInterest(new BigDecimal("3"));
        offer2.setFee(new BigDecimal("2"));
        offer2.setStatus("REQUESTED");
        offer2.setRequestDate(oldDate);
        offer2.setCar(car);
        offer2 = this.offerRepository.saveAndFlush(offer2);
        Map<String, Integer> offers = offerService.getLastWeekOffers();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yy");
        String date = LocalDateTime.now().format(format);
        Assert.assertEquals(7, offers.size());
        Integer actual = offers.get(date);
        Assert.assertEquals(java.util.Optional.of(1), java.util.Optional.of(actual));
    }

    @Test
    public void offerService_countOverdueRequest_ReturnsCorrect() {
        OfferService offerService = new OfferServiceImpl(this.offerRepository, this.carService, this.modelMapper);
        offer = this.offerRepository.saveAndFlush(offer);
        LocalDateTime oldDate = LocalDateTime.now().minusDays(1);
        Offer offer2 = new Offer();
        offer2.setDeposit(10);
        offer2.setTerm(48);
        offer2.setResidualValue(10);
        offer2.setInterest(new BigDecimal("3"));
        offer2.setFee(new BigDecimal("2"));
        offer2.setStatus("REQUESTED");
        offer2.setRequestDate(oldDate);
        offer2.setCar(car);
        offer2 = this.offerRepository.saveAndFlush(offer2);
        Integer actual = offerService.countOverdueRequest();
        Assert.assertEquals(java.util.Optional.of(1), java.util.Optional.of(actual));
    }

    @Test
    public void offerService_countOverdueRequestWithEmpty_ReturnsCorrect() {
        OfferService offerService = new OfferServiceImpl(this.offerRepository, this.carService, this.modelMapper);
        offer = this.offerRepository.saveAndFlush(offer);
        Offer offer2 = new Offer();
        offer2.setDeposit(10);
        offer2.setTerm(48);
        offer2.setResidualValue(10);
        offer2.setInterest(new BigDecimal("3"));
        offer2.setFee(new BigDecimal("2"));
        offer2.setStatus("REQUESTED");
        offer2.setRequestDate(LocalDateTime.now());
        offer2.setCar(car);
        offer2 = this.offerRepository.saveAndFlush(offer2);
        Integer actual = offerService.countOverdueRequest();
        Assert.assertEquals(java.util.Optional.of(0), java.util.Optional.of(actual));
    }

    @Test
    public void offerService_reviewOfferWithCorrectValue_ReturnsCorrect() {
        OfferService offerService = new OfferServiceImpl(this.offerRepository, this.carService, this.modelMapper);
        offer = this.offerRepository.saveAndFlush(offer);
        OfferServiceModel edited = new OfferServiceModel();
        edited.setDeposit(15);
        edited.setTerm(36);
        edited.setResidualValue(0);
        edited.setInterest(new BigDecimal("3.5"));
        edited.setFee(new BigDecimal("1.5"));
        edited.setStatus("REVISED");
        OfferServiceModel actual = offerService.reviewOffer(offer.getId(), edited);
        Assert.assertEquals(offer.getId(), actual.getId());
        Assert.assertEquals(edited.getDeposit(), actual.getDeposit());
        Assert.assertEquals(edited.getTerm(), actual.getTerm());
        Assert.assertEquals(edited.getResidualValue(), actual.getResidualValue());
        Assert.assertEquals(edited.getInterest(), actual.getInterest());
        Assert.assertEquals(edited.getFee(), actual.getFee());
        Assert.assertEquals(edited.getStatus(), actual.getStatus());
    }

    @Test(expected = OfferNotFoundException.class)
    public void offerService_reviewOfferWithWrongValue_Throws() {
        OfferService offerService = new OfferServiceImpl(this.offerRepository, this.carService, this.modelMapper);
        offer = this.offerRepository.saveAndFlush(offer);
        OfferServiceModel edited = new OfferServiceModel();
        edited.setDeposit(15);
        edited.setTerm(36);
        edited.setResidualValue(0);
        edited.setInterest(new BigDecimal("3.5"));
        edited.setFee(new BigDecimal("1.5"));
        edited.setStatus("REVISED");
        OfferServiceModel actual = offerService.reviewOffer("123", edited);
    }

    @Test
    public void offerService_changeOfferStatusWithCorrectValue_ReturnsCorrect() {
        OfferService offerService = new OfferServiceImpl(this.offerRepository, this.carService, this.modelMapper);
        offer = this.offerRepository.saveAndFlush(offer);

        OfferServiceModel actual = offerService.changeOfferStatus(offer.getId(), "REVISED");
        Assert.assertEquals("REVISED", actual.getStatus());
    }

    @Test(expected = OfferNotFoundException.class)
    public void offerService_changeOfferStatusWithWrong_Throws() {
        OfferService offerService = new OfferServiceImpl(this.offerRepository, this.carService, this.modelMapper);
        offer = this.offerRepository.saveAndFlush(offer);

        OfferServiceModel actual = offerService.changeOfferStatus("123", "REVISED");
    }

    @Test
    public void offerService_deleteOfferWithCorrectValue_ReturnsCorrect() {
        OfferService offerService = new OfferServiceImpl(this.offerRepository, this.carService, this.modelMapper);
        offer = this.offerRepository.saveAndFlush(offer);

        offerService.deleteOffer(offer.getId());
        Long actualCount = this.offerRepository.count();
        Assert.assertEquals(java.util.Optional.of(0L), java.util.Optional.of(actualCount));
    }

    @Test(expected = OfferNotFoundException.class)
    public void offerService_deleteOfferWithWrong_Throws() {
        OfferService offerService = new OfferServiceImpl(this.offerRepository, this.carService, this.modelMapper);
        offer = this.offerRepository.saveAndFlush(offer);

        offerService.deleteOffer("123");
    }
}
