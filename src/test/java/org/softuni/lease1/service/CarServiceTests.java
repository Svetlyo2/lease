package org.softuni.lease1.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.softuni.lease1.domain.entity.*;
import org.softuni.lease1.domain.model.service.CarServiceModel;
import org.softuni.lease1.domain.model.service.SellerServiceModel;
import org.softuni.lease1.domain.model.service.UserServiceModel;
import org.softuni.lease1.error.CarNotFoundException;
import org.softuni.lease1.repository.CarRepository;
import org.softuni.lease1.repository.SellerRepository;
import org.softuni.lease1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class CarServiceTests {
    @Autowired
    private CarRepository carRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SellerRepository sellerRepository;
    private ModelMapper modelMapper;
    private Car car;
    private UserService userService;
    private User user;
//    private RoleService roleService;
//    private BCryptPasswordEncoder encoder;

    @Before
    public void init() {
        this.modelMapper = new ModelMapper();
//        this.userService = new UserServiceImpl();
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
    }

//    @Test
//    public void carService_addWithCorrectValues_ReturnsCorrect(){
//        CarService carService = new CarServiceImpl(this.carRepository, this.userRepository, this.userService, this.modelMapper);
////        UserService userService = new UserServiceImpl(this.userRepository, this.roleService, this.modelMapper, this.encoder);
////        UserServiceModel userServiceModel=userService.findByUsername(user.getUsername());
//        CarServiceModel toBeAdded = new CarServiceModel();
//        toBeAdded.setCarCondition(CarCondition.NEW);
//        toBeAdded.setMake("Opel");
//        toBeAdded.setCarModel("Astra");
//        toBeAdded.setYear(2015);
//        toBeAdded.setMileage(66000);
//        toBeAdded.setPrice(new BigDecimal("12000"));
//        toBeAdded.setSalesperson("Misho");
//        toBeAdded.setEmail("m@mail.bg");
//        toBeAdded.setMobile("0222323232");
//        CarServiceModel actual = carService.add(toBeAdded, user.getUsername());
//        Assert.assertEquals(toBeAdded.getMake(), actual.getMake());
//    }

    @Test
    public void carService_findCarByIdWithCorrectValues_ReturnsCorrect() {
        CarService carService = new CarServiceImpl(this.carRepository, this.userRepository, this.userService, this.modelMapper);
        car = this.carRepository.saveAndFlush(car);
        CarServiceModel actual = carService.findCarById(car.getId());
        Assert.assertEquals(car.getId(), actual.getId());
        Assert.assertEquals(car.getMake(), actual.getMake());
        Assert.assertEquals(car.getCarModel(), actual.getCarModel());
        Assert.assertEquals(car.getYear(), actual.getYear());
        Assert.assertEquals(car.getPrice(), actual.getPrice());
        Assert.assertEquals(car.getCarCondition(), actual.getCarCondition());
        Assert.assertEquals(car.getSalesperson(), actual.getSalesperson());
        Assert.assertEquals(car.getEmail(), actual.getEmail());
        Assert.assertEquals(car.getMobile(), actual.getMobile());
    }
    @Test(expected = CarNotFoundException.class)
    public void carService_findCarByIdWithWrong_ReturnsNull() {
        CarService carService = new CarServiceImpl(this.carRepository, this.userRepository, this.userService, this.modelMapper);
        car = this.carRepository.saveAndFlush(car);
        CarServiceModel actual = carService.findCarById("123");
    }
    @Test
    public void carService_setSellerWithCorrectValues_ReturnsCorrect() {
        CarService carService = new CarServiceImpl(this.carRepository, this.userRepository, this.userService, this.modelMapper);
        car = this.carRepository.saveAndFlush(car);
        Seller seller = new Seller();
        seller.setType(Type.PERSON);
        seller.setFullName("Misho");
        seller.setUIC("3232325656");
        seller.setCity("Varna");
        seller.setAddress("kv. Glarus");
        sellerRepository.saveAndFlush(seller);
        carService.setSeller(this.modelMapper.map(seller, SellerServiceModel.class), car.getId());
        CarServiceModel actual = carService.findCarById(car.getId());
        Assert.assertEquals(seller.getFullName(), actual.getSeller().getFullName());
    }

    @Test
    public void carService_findCarsByUsernameWithCorrectValues_ReturnsCorrect() {
        CarService carService = new CarServiceImpl(this.carRepository, this.userRepository, this.userService, this.modelMapper);
        car = this.carRepository.saveAndFlush(car);
        User user2 = new User();
        user2.setUsername("pepi");
        user2.setPassword("123");
        user2.setEmail("pepi@abv.bg");
        user2 = this.userRepository.saveAndFlush(user2);
        Car car2 = new Car();
        car2.setCarCondition(CarCondition.NEW);
        car2.setMake("VW");
        car2.setCarModel("Golf");
        car2.setYear(2017);
        car2.setMileage(33000);
        car2.setPrice(new BigDecimal("16000"));
        car2.setSalesperson("Misho");
        car2.setEmail("m@mail.bg");
        car2.setMobile("0222323232");
        car2.setUser(user2);
        car2 = this.carRepository.saveAndFlush(car2);
//        List<CarServiceModel> cars = carService.findCarsByUser(user2.getUsername());
//        Assert.assertEquals(1, cars.size());


    }




    }
