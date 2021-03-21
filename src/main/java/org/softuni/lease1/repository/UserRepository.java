package org.softuni.lease1.repository;

import org.softuni.lease1.domain.entity.User;
import org.softuni.lease1.domain.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String > {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
}
