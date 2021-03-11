package org.softuni.lease1.service;

import org.modelmapper.ModelMapper;
import org.softuni.lease1.domain.entity.UserProfile;
import org.softuni.lease1.domain.model.service.ProfileServiceModel;
import org.softuni.lease1.domain.model.service.UserServiceModel;
import org.softuni.lease1.repository.UserProfileRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class UserProfileServiceIml implements UserProfileService{
    private final UserProfileRepository userProfileRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;


    public UserProfileServiceIml(UserProfileRepository userProfileRepository,
                                 UserService userService,
                                 ModelMapper modelMapper) {
        this.userProfileRepository = userProfileRepository;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @Override
    public void add(ProfileServiceModel profileServiceModel, String name) {
        UserServiceModel userServiceModel = userService.findByUsername(name);
        profileServiceModel.setUser(userServiceModel);
        this.userProfileRepository.saveAndFlush(this.modelMapper.map(profileServiceModel, UserProfile.class));

    }

    @Override
    public ProfileServiceModel findProfile(String name) {
        UserProfile userProfile = this.userProfileRepository.findByUserUsernameContains(name).orElse(null);
        if (userProfile == null){
            return null;
        }
        return this.modelMapper.map(userProfile, ProfileServiceModel.class);
    }

    @Override
    public ProfileServiceModel editProfile(ProfileServiceModel profileServiceModel, String name) {

        UserProfile userProfile = this.userProfileRepository.findByUserUsernameContains(name)
                .orElseThrow(()-> new UsernameNotFoundException("Username not found"));
        return this.modelMapper.map(this.userProfileRepository.saveAndFlush(userProfile), ProfileServiceModel.class);
    }
}
