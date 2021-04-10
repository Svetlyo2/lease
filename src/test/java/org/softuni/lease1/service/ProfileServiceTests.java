package org.softuni.lease1.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.softuni.lease1.domain.entity.Type;
import org.softuni.lease1.domain.entity.User;
import org.softuni.lease1.domain.entity.UserProfile;
import org.softuni.lease1.domain.model.service.ProfileServiceModel;
import org.softuni.lease1.domain.model.service.UserServiceModel;
import org.softuni.lease1.repository.UserProfileRepository;
import org.softuni.lease1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class ProfileServiceTests {
    @Autowired
    private UserProfileRepository userProfileRepository;
    @Autowired
    private UserRepository userRepository;
    private ModelMapper modelMapper;
    private UserService userService;
    private User user;
    private UserProfile profile;
    private RoleService roleService;
    private BCryptPasswordEncoder encoder;

    @Before
    public void init() {
        this.modelMapper = new ModelMapper();
//        this.userService = new UserServiceImpl();
        user = new User();
        user.setUsername("kiko");
        user.setPassword("123");
        user.setEmail("k@abv.bg");
        user = this.userRepository.saveAndFlush(user);
        profile = new UserProfile();
        profile.setType(Type.PERSON);
        profile.setFullName("Kiro Kirov");
        profile.setUIC("1231235656");
        profile.setCity("Varna");
        profile.setAddress("Morska");
        profile.setMobile("0777565656");
        profile.setUser(user);
    }

//    @Test
//    public void profileService_addProfileWithCorrectValues_ReturnsCorrect(){
//        UserProfileService userProfileService = new UserProfileServiceIml(this.userProfileRepository, this.userService, this.modelMapper);
//        this.userService = new UserServiceImpl(this.userRepository, this.roleService, this.modelMapper, this.encoder);
//
//        ProfileServiceModel toBeSaved = new ProfileServiceModel();
//        toBeSaved.setType(Type.PERSON);
//        toBeSaved.setFullName("Kiro Kirov");
//        toBeSaved.setUIC("1231235656");
//        toBeSaved.setCity("Varna");
//        toBeSaved.setAddress("Morska");
//        toBeSaved.setMobile("0777565656");
//        UserServiceModel serviceModel = this.userService.findAllUsers().get(0);
//        ProfileServiceModel actual = userProfileService.add(toBeSaved, "kiko" );
//        ProfileServiceModel expected = this.modelMapper.map(this.userProfileRepository.findAll().get(0), ProfileServiceModel.class);
//        Assert.assertEquals(expected.getId(), actual.getId());
//        Assert.assertEquals(expected.getType(), actual.getType());
//        Assert.assertEquals(expected.getFullName(), actual.getFullName());
//        Assert.assertEquals(expected.getUIC(), actual.getUIC());
//        Assert.assertEquals(expected.getCity(), actual.getCity());
//        Assert.assertEquals(expected.getAddress(), actual.getAddress());
//        Assert.assertEquals(expected.getMobile(), actual.getMobile());
//
//    }

    @Test
    public void userService_findProfileWithCorrect_ReturnsCorrect() {
        UserProfileService userProfileService = new UserProfileServiceIml(this.userProfileRepository, this.userService, this.modelMapper);

        profile = this.userProfileRepository.saveAndFlush(profile);
        ProfileServiceModel actual = userProfileService.findProfile("kiko");
        Assert.assertEquals(profile.getId(), actual.getId());
        Assert.assertEquals(profile.getType(), actual.getType());
        Assert.assertEquals(profile.getFullName(), actual.getFullName());
        Assert.assertEquals(profile.getUIC(), actual.getUIC());
        Assert.assertEquals(profile.getCity(), actual.getCity());
        Assert.assertEquals(profile.getAddress(), actual.getAddress());
        Assert.assertEquals(profile.getMobile(), actual.getMobile());
    }

    @Test
    public void userService_findProfileWithWrong_ReturnsNull() {
        UserProfileService userProfileService = new UserProfileServiceIml(this.userProfileRepository, this.userService, this.modelMapper);

        profile = this.userProfileRepository.saveAndFlush(profile);
        ProfileServiceModel actual = userProfileService.findProfile("123");
        Assert.assertNull(actual);
    }

    @Test
    public void userService_editProfileWithCorrect_ReturnsCorrect() {
        UserProfileService userProfileService = new UserProfileServiceIml(this.userProfileRepository, this.userService, this.modelMapper);
        profile = this.userProfileRepository.saveAndFlush(profile);
        ProfileServiceModel edited = new ProfileServiceModel();
        edited.setType(Type.COMPANY);
        edited.setFullName("Kiril Petrov");
        edited.setUIC("5656568989");
        edited.setCity("Plovdiv");
        edited.setAddress("Tepeto");
        edited.setMobile("0123232323");
        ProfileServiceModel actual = userProfileService.editProfile(edited,user.getUsername());
        Assert.assertEquals(edited.getType(), actual.getType());
        Assert.assertEquals(edited.getFullName(), actual.getFullName());
        Assert.assertEquals(edited.getUIC(), actual.getUIC());
        Assert.assertEquals(edited.getCity(), actual.getCity());
        Assert.assertEquals(edited.getAddress(), actual.getAddress());
        Assert.assertEquals(edited.getMobile(), actual.getMobile());
    }
    @Test(expected = UsernameNotFoundException.class)
    public void userService_editProfileWithWrongUsername_Throws() {
        UserProfileService userProfileService = new UserProfileServiceIml(this.userProfileRepository, this.userService, this.modelMapper);
        profile = this.userProfileRepository.saveAndFlush(profile);
        ProfileServiceModel edited = new ProfileServiceModel();
        edited.setType(Type.COMPANY);
        edited.setFullName("Kiril Petrov");
        edited.setUIC("5656568989");
        edited.setCity("Plovdiv");
        edited.setAddress("Tepeto");
        edited.setMobile("0123232323");
        ProfileServiceModel actual = userProfileService.editProfile(edited, "123");
    }
}
