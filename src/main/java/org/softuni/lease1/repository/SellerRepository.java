package org.softuni.lease1.repository;

import org.softuni.lease1.domain.entity.Seller;
import org.softuni.lease1.domain.model.service.SellerServiceModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SellerRepository extends JpaRepository<Seller, String > {

//    Optional<Seller> findByCar_Id(String carId);
    Optional<Seller> findByUIC(String uic);
}
