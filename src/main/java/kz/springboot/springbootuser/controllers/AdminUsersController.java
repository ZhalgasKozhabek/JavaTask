package kz.springboot.springbootuser.controllers;

import kz.springboot.springbootuser.entities.Category;
import kz.springboot.springbootuser.entities.Item;
import kz.springboot.springbootuser.entities.Roles;
import kz.springboot.springbootuser.entities.Users;
import kz.springboot.springbootuser.services.RoleService;
import kz.springboot.springbootuser.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.management.relation.Role;
import java.util.ArrayList;
import java.util.List;

@Controller(value = "/admin/users")
public class AdminUsersController {
    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @GetMapping(value = "/admin/users")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
    public String adminCategories(Model model){
        List<Users> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "admin/users_table";
    }


    @GetMapping(value="/admin/users/profile")
    @PreAuthorize("isAuthenticated()")
    public String profile(Model model, @RequestParam(name = "email") String email){
        model.addAttribute("user", userService.getUserByEmail(email));
        return "admin/userDetailAdmin";
    }


    @PostMapping(value = "/admin/users/update")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
    public String updateRole(@RequestParam(name = "old_email") String old_email,
                                 @RequestParam(name = "email") String email,
                                 @RequestParam(name="full_name") String full_name){
        Users user = userService.getUserByEmail(old_email);
        if(user != null) {
            user.setEmail(email);
            user.setFullname(full_name);

            userService.updateUserProfile(user, old_email);
            return "redirect:/admin/users";
        }

        return "error";
    }


    @PostMapping(value="/admin/users/delete")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
    public String deleteUser(@RequestParam(name = "email") String email){
        Users user = userService.getUserByEmail(email);
        if(user != null) {
            userService.deleteUser(user);
            return "redirect:/admin/items";
        }
        return "error";
    }



    @PostMapping(value="/admin/users/assignRole")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
    public String assignRole(@RequestParam(name = "email") String email,
                                 @RequestParam(name = "role_id") Long role_id){

        Roles role = roleService.getRolesById(role_id);
        System.out.println("I am here");
        if(role != null) {
            System.out.println("role = " + role);
            Users user = userService.getUserByEmail(email);
            if (user != null) {
                System.out.println("user = " + user);

                List<Roles> roles = user.getRoles();

                if(roles == null)
                    roles = new ArrayList<>();

                roles.add(role);
                user.setRoles(roles);
                userService.saveUser(user);
                return "redirect:/admin/users/profile?email=" + user.getEmail();
            }
        }

        return "error";
    }

    @PostMapping(value="/admin/users/revokeRole")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
    public String revokeRole(@RequestParam(name = "email") String email,
                                 @RequestParam(name = "role_id") Long role_id){

        Roles role = roleService.getRole(role_id);
        if(role != null) {
            Users user = userService.getUserByEmail(email);
            if (user != null) {
                List<Roles> roles = user.getRoles();

                if(roles == null)
                    roles = new ArrayList<>();

                roles.remove(role);
                user.setRoles(roles);
                userService.saveUser(user);
                return "redirect:/admin/users/profile?email=" + user.getEmail();
            }
        }

        return "error";
    }
}
