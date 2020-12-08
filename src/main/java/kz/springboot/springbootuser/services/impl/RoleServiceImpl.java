package kz.springboot.springbootuser.services.impl;

import kz.springboot.springbootuser.entities.Roles;
import kz.springboot.springbootuser.repositories.RoleRepository;
import kz.springboot.springbootuser.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public Roles getRole(Long id){
        return roleRepository.getOne(id);
    }

    @Override
    public List<Roles> getAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public Roles getRolesById(Long id) {
        return roleRepository.getOne(id);
    }

    public Roles getRolesByName(String name){
        return roleRepository.getRolesByName(name);
    }
}
