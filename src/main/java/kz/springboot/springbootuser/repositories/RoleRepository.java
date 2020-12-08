package kz.springboot.springbootuser.repositories;

import kz.springboot.springbootuser.entities.Roles;
import kz.springboot.springbootuser.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.management.relation.Role;
import javax.transaction.Transactional;

@Repository
@Transactional
public interface RoleRepository extends JpaRepository<Roles, Long> {
    Roles getRolesByName(String name);


    Roles findByName(String name);
}
