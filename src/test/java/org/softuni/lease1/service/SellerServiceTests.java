package org.softuni.lease1.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.softuni.lease1.domain.entity.Type;
import org.softuni.lease1.domain.model.service.SellerServiceModel;
import org.softuni.lease1.repository.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class SellerServiceTests {
    @Autowired
    private SellerRepository sellerRepository;
    private OfferService offerService;
    private CarService carService;
    private ModelMapper modelMapper;

    @Before
    public void init(){
        this.modelMapper = new ModelMapper();
    }

    @Test
    public void sellerService_addSellerWithCorrectValues_ReturnsCorrect(){
        SellerService sellerService = new SellerServiceImpl(this.sellerRepository, this.offerService, this.carService, this.modelMapper);
        SellerServiceModel toBeSaved = new SellerServiceModel();
        toBeSaved.setFullName("Misho Mishev");
        toBeSaved.setUIC("2323235656");
        toBeSaved.setType(Type.PERSON);
        toBeSaved.setCity("Sofia");
        toBeSaved.setAddress("Gorublyane");
        //cars
//        SellerServiceModel actual = sellerService.add(toBeSaved,"");

    }
}
