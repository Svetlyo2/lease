package org.softuni.lease1.repository;

import org.softuni.lease1.domain.entity.Employee;
import org.softuni.lease1.domain.entity.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String > {
    Optional<Employee> findByUser_Username(String username);
}
