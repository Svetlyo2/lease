package org.softuni.lease1.service;

import org.softuni.lease1.domain.model.service.ProfileServiceModel;


public interface UserProfileService {
    void add(ProfileServiceModel profileServiceModel, String name);
    ProfileServiceModel findProfile(String name);
    ProfileServiceModel editProfile(ProfileServiceModel profileServiceModel, String name);

}
