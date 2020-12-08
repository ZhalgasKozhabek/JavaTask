package kz.springboot.springbootuser.controllers;

import kz.springboot.springbootuser.entities.Category;
import kz.springboot.springbootuser.entities.Users;
import kz.springboot.springbootuser.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller(value = "/user")
public class UsersController {
    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping(value = "/user/update_profile")
    public String update_profile(@RequestParam(name = "email") String email,
                             @RequestParam(name = "full_name") String full_name){


        Users user = getUserData();
        assert user != null;
        String old_email = user.getEmail();
        user.setEmail(email);
        user.setFullname(full_name);
        if(userService.updateUserProfile(user, old_email) != null){
            return "redirect:/profile?success";

        }

        return "redirect:/profile?error";
    }


    @PostMapping(value = "/user/update_password")
    public String update_password(@RequestParam(name = "old_password") String old_password,
                             @RequestParam(name = "new_password") String new_password,
                             @RequestParam(name = "retype_new_password") String retype_new_password){


        Users user = getUserData();
        assert user != null;
        System.out.println("old_password =" + old_password + "\nold_pass_enc =" + user.getPassword());
        if(bCryptPasswordEncoder.matches(old_password, user.getPassword())) {
            System.out.println("STEP 1");
            if(new_password.equals(retype_new_password)) {
                System.out.println("STEP 2");
                user.setPassword(new_password);
                if (userService.updateUserPassword(user) != null) {
                    System.out.println("STEP 3");
                    return "redirect:/profile?success";
                }
            }
        }

        return "redirect:/profile?error";
    }


    private Users getUserData(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof AnonymousAuthenticationToken)){
            User secUser = (User)authentication.getPrincipal();
            Users myUser = userService.getUserByEmail(secUser.getUsername());
            return myUser;
        }

        return null;
    }


}
