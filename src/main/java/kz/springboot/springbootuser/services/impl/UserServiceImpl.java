package kz.springboot.springbootuser.services.impl;

import kz.springboot.springbootuser.entities.Roles;
import kz.springboot.springbootuser.entities.Users;
import kz.springboot.springbootuser.repositories.RoleRepository;
import kz.springboot.springbootuser.repositories.UserRepository;
import kz.springboot.springbootuser.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

//    @Override

    @Override
    public Users saveUser(Users user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Users user) {
        userRepository.delete(user);
    }
//    public Users getUserByID(Long id) { return userRepository.findById(id);}

    @Override
    public List<Users> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Users myUser = userRepository.findByEmail(s);
        if(myUser != null){
            return new User(myUser.getEmail(), myUser.getPassword(), myUser.getRoles());
        }

        throw new UsernameNotFoundException("User Not Found");
    }

    @Override
    public Users getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }


    @Override
    public Users createUser(Users user) {
        Users checkUser = userRepository.findByEmail(user.getEmail());

        if(checkUser == null){
            Roles role = roleRepository.findByName("ROLE_USER");
            if(role != null){
                ArrayList<Roles> roles = new ArrayList<>();
                roles.add(role);
                user.setRoles(roles);
                user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
                return userRepository.save(user);

            }
        }
        return null;
    }

    @Override
    public Users updateUserProfile(Users user, String old_email) {
        Users checkUser = userRepository.findByEmail(old_email);
        if(checkUser != null){
            return userRepository.save(user);
        }
        return null;
    }

    @Override
    public Users updateUserPassword(Users user) {
        Users checkUser = userRepository.findByEmail(user.getEmail());
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        if(checkUser != null){
            return userRepository.save(user);
        }
        return null;
    }
}
