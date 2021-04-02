package org.softuni.lease1.repository;

import org.softuni.lease1.domain.entity.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OfferRepository extends JpaRepository<Offer, String> {
    List<Offer> findAllByStatusIsContaining(String status);
    List<Offer> findAllByRequestDateAfter(LocalDateTime date);

}
