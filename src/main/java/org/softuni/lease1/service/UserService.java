package org.softuni.lease1.service;

import org.softuni.lease1.domain.model.service.UserServiceModel;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    UserServiceModel registerUser(UserServiceModel userServiceModel);
    UserServiceModel findByUsername(String username);
    UserServiceModel editUser(UserServiceModel userServiceModel, String oldPassword);
    List<UserServiceModel> findAllUsers();
}
