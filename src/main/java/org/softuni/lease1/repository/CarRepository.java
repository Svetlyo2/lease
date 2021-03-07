package org.softuni.lease1.repository;

import org.softuni.lease1.domain.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarRepository extends JpaRepository<Car, String > {
    Optional<List<Car>>findAllByUser_Username(String username);
}
