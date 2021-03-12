package org.softuni.lease1.repository;

import org.softuni.lease1.domain.entity.AppStatus;
import org.softuni.lease1.domain.entity.LeaseApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LeaseApplicationRepository extends JpaRepository<LeaseApplication, String > {

    Optional<LeaseApplication> findByUser_Id(String userId);
    List<LeaseApplication> findAllByAppStatus(AppStatus status);
}
