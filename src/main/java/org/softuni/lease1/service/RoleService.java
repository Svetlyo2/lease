package org.softuni.lease1.service;

import org.softuni.lease1.domain.model.service.RoleServiceModel;

import java.util.Set;

public interface RoleService {
    void seedRolesInDb();
    Set<RoleServiceModel> findAllRoles();
    RoleServiceModel findByAuthority(String authority);
//    void assignUserRoles(UserServiceModel userServiceModel, long numberOfUsers);
}
