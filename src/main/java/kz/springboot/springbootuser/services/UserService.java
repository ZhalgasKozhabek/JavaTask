package kz.springboot.springbootuser.services;

import kz.springboot.springbootuser.entities.Users;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    Users getUserByEmail(String username);

    //    Users getUserByID(Long id);
    Users createUser(Users user);
    Users updateUserProfile(Users user, String email);
    Users updateUserPassword(Users user);

    Users saveUser(Users user);
    void deleteUser(Users user);
    List<Users> getAllUsers();
}
