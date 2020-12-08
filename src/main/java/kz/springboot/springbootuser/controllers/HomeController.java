package kz.springboot.springbootuser.controllers;

import kz.springboot.springbootuser.entities.*;
import kz.springboot.springbootuser.repositories.UserRepository;
import kz.springboot.springbootuser.services.BrandService;
import kz.springboot.springbootuser.services.ItemService;
import kz.springboot.springbootuser.services.RoleService;
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Controller(value = "/")
public class HomeController {

    @Autowired
    private ItemService itemService;

//    @Autowired
//    private BrandService brandService;

    @Autowired
    private UserService userService;

//    @Autowired
//    private UserRepository userRepository;

//    @Autowired
//    private RoleService roleService;

    @GetMapping(value = "/")
    public String index(Model model, @RequestParam(name = "category_id", defaultValue = "-1", required = false) Long category_id){
        List<Item> items;
        Category cat = itemService.getCategory(category_id);

        if(category_id > 0 && cat != null)
            items = itemService.findAllByCategoriesContains(cat);

        else
            items = itemService.getAllItems();

        model.addAttribute("currentUser", getUserData());
        model.addAttribute("items", items);
        model.addAttribute("categories", itemService.getAllCategories());
        model.addAttribute("brands", itemService.getAllBrands());
        return "user/index";
    }


    @GetMapping(value = "/search")
    public String advancedSearch(Model model,
                                 @RequestParam(name = "name", defaultValue = "all", required = false) String name,
                                 @RequestParam(name = "brand_id", defaultValue = "-1", required = false) Long brand_id,
                                 @RequestParam(name = "price_from", defaultValue = "0", required = false) int price_from,
                                 @RequestParam(name = "price_to", defaultValue = "1000000000", required = false) int price_to,
                                 @RequestParam(name = "order", defaultValue = "asc", required = false) String order){

        List<Item> items;

        if (order.equals("asc"))
            items = itemService.findAllByNameContainsAndPriceBetweenAndBrandEqualsOrderByPriceAsc(name, price_from, price_to, itemService.getBrand(brand_id));
        else
            items = itemService.findAllByNameContainsAndPriceBetweenAndBrandEqualsOrderByPriceDesc(name, price_from, price_to, itemService.getBrand(brand_id));


        System.out.println("Check to null = " + items);
        System.out.println("price_from = " + price_from);
        System.out.println("price_to = " + price_to);
        if(items.size() == 0 && price_from == 0 && price_to == 1000000000) {
            items = itemService.findAllByNameContains(name);
            System.out.println("Name = " + items);

        }

        if (! items.isEmpty()) {
            brand_id = items.get(0).getBrand().getId();
        }

//        if(items == null)
//            items = itemService.getAllItems();


        model.addAttribute("items", items);
        model.addAttribute("categories", itemService.getAllCategories());
        model.addAttribute("brands", itemService.getAllBrands());
        model.addAttribute("brand_id", brand_id);
        return "user/advancedSearch";
    }


    @GetMapping(value = "/result")
    public String searchResult(Model model,
                               @RequestParam(name = "word", defaultValue = "all", required = false) String word,
                               @RequestParam(name = "orderBy", defaultValue = "asc", required = false) String orderBy,
                               @RequestParam(name = "price_from", defaultValue = "0", required = false) int priceFrom,
                               @RequestParam(name = "price_to", defaultValue = "0", required = false) int priceTo){
        if(word.equals("all"))
            return "redirect:/";

        List<Item> items;

        if(priceTo == 0)
            priceTo = 100000000;

        System.out.println("price_from = " + priceFrom);
        System.out.println("price_to = " + priceTo);


        if(orderBy.equals("desc"))
            items = itemService.findAllByNameContainsAndPriceBetweenOrderByPriceDesc(word, priceFrom, priceTo);

        else
            items = itemService.findAllByNameContainsAndPriceBetweenOrderByPriceAsc(word, priceFrom, priceTo);



        model.addAttribute("items", items);
        model.addAttribute("word", word);
        return "user/searchResult";
    }


    @GetMapping(value = "/item")
    public String itemDetail(Model model, @RequestParam(name = "id", defaultValue = "1", required = true) Long id){
        Item item = itemService.getItem(id);
        if(item != null) {
            model.addAttribute("item", item);
            model.addAttribute("categories", itemService.getAllCategories());
            model.addAttribute("brands", itemService.getAllBrands());
            return "user/itemDetail";
        }

        return "error";
    }


    @GetMapping(value = "/403")
    public String accessDenied(Model model){
        return "403";
    }


    @GetMapping(value = "/login")
    public String login(Model model,
                        @RequestParam(name = "error", defaultValue = "1", required = false) String error,
                        @RequestParam(name = "email", defaultValue = "", required = false) String email){
        model.addAttribute("email", email);
        return "login";
    }


    @GetMapping(value = "/register")
    public String register(Model model, @RequestParam(name = "error", required = false) String error){
        model.addAttribute("currentUser", getUserData());
        return "register";
    }


    @PostMapping(value = "/register")
    public String toRegister(@RequestParam(name = "email") String email,
                             @RequestParam(name = "password") String password,
                             @RequestParam(name = "retype_password") String re_password,
                             @RequestParam(name = "full_name") String full_name){

        if(password.equals(re_password)){
            Users user = new Users(email, password, full_name);
            if(userService.createUser(user) != null){
                return "redirect:/login?email="+email;

            }
        }
        return "redirect:/register?error";
    }


    @GetMapping(value="/profile")
    @PreAuthorize("isAuthenticated()")
    public String profile(Model model){
        model.addAttribute("currentUser", getUserData());
        System.out.println("User = " + getUserData());
        return "user/profile";
    }


//    public boolean saveUser(Users user) {
//        Users myUser = getUserData();
//
//        if (myUser != null) {
//            return false;
//        }
//
////        user.setRoles(Collections.singleton(roleService.getRolesByName("ROLE_USER")));
//
//        userRepository.save(user);
//        return true;
//    }


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
