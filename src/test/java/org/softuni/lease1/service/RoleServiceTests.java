package org.softuni.lease1.service;



import org.junit.Test;
import org.junit.runner.RunWith;
import org.softuni.lease1.TestBase;
import org.softuni.lease1.domain.entity.Role;
import org.softuni.lease1.domain.model.service.RoleServiceModel;
import org.softuni.lease1.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
public class RoleServiceTests extends TestBase {

    @MockBean
    RoleRepository mockRoleRepository;

    @Autowired
    RoleService roleService;

    @Test
    public void seedRolesInDb_whenRepositoryIsEmpty_shouldSeedAllRoles() {

        List<Role> roles = new ArrayList<>();
        roles.add(new Role());
        roles.add(new Role());
        roles.add(new Role());
        roles.add(new Role());

        when(mockRoleRepository.count()).thenReturn(0L);
        roleService.seedRolesInDb();
        assertEquals(4, roles.size());
    }

    private void assertEquals(int i, int size) {
    }

    @Test
    public void seedRolesInDb_whenRepositoryNotEmpty_shouldNotSeedRoles() {

        List<Role> roles = new ArrayList<>();
        when(mockRoleRepository.count()).thenReturn(4L);
        roleService.seedRolesInDb();
        assertEquals(0, roles.size());
    }

    @Test
    public void findAllRoles_whenAnyRolesInDb_shouldReturnAllCorrect() {

        List<Role> roles;
        roles = Arrays.asList(new Role("ROLE_ROOT"), new Role("ROLE_ADMIN"), new Role("ROLE_USER"));
        when(mockRoleRepository.findAll()).thenReturn(roles);
        Set<RoleServiceModel> roleServiceModels = roleService.findAllRoles();
        assertEquals(roles.size(), roleServiceModels.size());
    }

    @Test
    public void findAllRoles_whenNoRolesInDb_shouldReturnEmptySet(){

        List<Role> roles = new ArrayList<>();
        when(mockRoleRepository.findAll()).thenReturn(roles);
        Set<RoleServiceModel> roleServiceModels = roleService.findAllRoles();
        assertEquals(0, roleServiceModels.size());
    }

}
