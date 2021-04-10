package org.softuni.lease1.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.softuni.lease1.domain.entity.User;
import org.softuni.lease1.domain.model.service.UserServiceModel;
import org.softuni.lease1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class UserServiceTests {
    @Autowired
    private UserRepository userRepository;
    private RoleService roleService;
    private ModelMapper modelMapper;
    private BCryptPasswordEncoder encoder;


    @Before
    public void init() {
        this.modelMapper = new ModelMapper();
        this.encoder = new BCryptPasswordEncoder();
    }

//        @Test
//    public void userService_registerUserWithCorrectValues_ReturnsCorrect(){
//        UserService userService = new UserServiceImpl(this.userRepository,this.roleService,this.modelMapper,this.encoder);
//        RoleServiceModel roleServiceModel = new RoleServiceModel();
//        roleServiceModel.setAuthority("ROLE_USER");
//        Set<RoleServiceModel> authorities = new HashSet<>();
//        authorities.add(roleServiceModel);
//            this.roleService.seedRolesInDb();
//
//        UserServiceModel toBeSaved = new UserServiceModel();
//        toBeSaved.setUsername("pepi");
//        toBeSaved.setEmail("pepi@abv.bg");
//        toBeSaved.setPassword("123");
////        toBeSaved.setAuthorities(authorities);
//        UserServiceModel actual = userService.registerUser(toBeSaved);
//        UserServiceModel expected = this.modelMapper.map(this.userRepository.findAll().get(0), UserServiceModel.class);
//
//        Assert.assertEquals(expected.getId(), actual.getId());
//        Assert.assertEquals(expected.getUsername(), actual.getUsername());
//        Assert.assertEquals(expected.getEmail(), actual.getEmail());
////        Assert.assertEquals(expected.getPassword(), actual.getPassword());
//        Assert.assertEquals(expected.getAuthorities(), actual.getAuthorities());
//    }

    @Test
    public void userService_editUserWithCorrect_ReturnsCorrect() {
        UserService userService = new UserServiceImpl(this.userRepository, this.roleService, this.modelMapper, this.encoder);

        User user = new User();
        user.setUsername("kiko");
        user.setPassword(this.encoder.encode("123"));
        user.setEmail("k@abv.bg");
        user = this.userRepository.saveAndFlush(user);
        UserServiceModel edited = new UserServiceModel();
        edited.setUsername("kiko");
        edited.setPassword("");
        edited.setEmail("kiril@abv.bg");
        UserServiceModel actual = userService.editUser(edited,"123");

        Assert.assertEquals(edited.getEmail(), actual.getEmail());
    }

    @Test
    public void userService_findByUsernameWithCorrect_ReturnsCorrect() {
        UserService userService = new UserServiceImpl(this.userRepository, this.roleService, this.modelMapper, this.encoder);

        User user = new User();
        user.setUsername("kiko");
        user.setPassword("123");
        user.setEmail("k@abv.bg");
        user = this.userRepository.saveAndFlush(user);

        UserServiceModel actual = userService.findByUsername("kiko");
        Assert.assertEquals(user.getUsername(), actual.getUsername());
    }

    @Test(expected = UsernameNotFoundException.class)
    public void userService_findByUsernameWithWrong_Throws() {
        UserService userService = new UserServiceImpl(this.userRepository, this.roleService, this.modelMapper, this.encoder);

        User user = new User();
        user.setUsername("kiko");
        user.setPassword("123");
        user.setEmail("k@abv.bg");
        user = this.userRepository.saveAndFlush(user);

        UserServiceModel actual = userService.findByUsername("gosho");
    }

    @Test
    public void userService_findAllUsers_ReturnsCorrect() {
        UserService userService = new UserServiceImpl(this.userRepository, this.roleService, this.modelMapper, this.encoder);

        User user = new User();
        user.setUsername("kiko");
        user.setPassword("123");
        user.setEmail("k@abv.bg");
        user = this.userRepository.saveAndFlush(user);

        User user2 = new User();
        user2.setUsername("koko");
        user2.setPassword("456");
        user2.setEmail("k2@abv.bg");
        user2 = this.userRepository.saveAndFlush(user2);
        List<UserServiceModel> actual = userService.findAllUsers();
        Assert.assertEquals(2, actual.size());
    }
}
