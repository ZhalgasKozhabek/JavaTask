package kz.springboot.springbootuser.controllers;

import kz.springboot.springbootuser.entities.Brand;
import kz.springboot.springbootuser.entities.Category;
import kz.springboot.springbootuser.entities.Country;
import kz.springboot.springbootuser.entities.Item;
import kz.springboot.springbootuser.services.BrandService;
import kz.springboot.springbootuser.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller(value = "/admin")
public class AdminController {

    @Autowired
    private ItemService itemService;

    @Autowired
    private BrandService brandService;

    @GetMapping(value = "/admin")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
    public String adminIndex(Model model){
        return "admin/adminIndex";
    }

}
