package org.softuni.lease1.repository;

import org.softuni.lease1.domain.entity.AppStatus;
import org.softuni.lease1.domain.entity.LeaseApplication;
import org.softuni.lease1.domain.model.service.LeaseApplicationServiceModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LeaseApplicationRepository extends JpaRepository<LeaseApplication, String > {

    List<LeaseApplication> findAllByUser_Id(String userId);
    List<LeaseApplication> findAllByUser_Username(String username);
    List<LeaseApplication> findAllByAppStatus(AppStatus status);
    Optional<LeaseApplicationServiceModel> findByOffer_Id(String id);
}
