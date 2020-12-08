package kz.springboot.springbootuser.services;

import kz.springboot.springbootuser.entities.Roles;

import java.util.List;

public interface RoleService {
    Roles getRole(Long id);
    Roles getRolesByName(String name);
    Roles getRolesById(Long id);
    List<Roles> getAllRoles();
}
