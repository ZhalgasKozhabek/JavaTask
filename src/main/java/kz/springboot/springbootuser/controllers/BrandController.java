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

@Controller(value = "/admin/brands")
public class BrandController {
    @Autowired
    private ItemService itemService;

    @Autowired
    private BrandService brandService;

    @GetMapping(value = "/admin/brands")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
    public String adminBrands(Model model){
        List<Country> countries = brandService.getAllCountries();
        List<Brand> brands = itemService.getAllBrands();
        model.addAttribute("countries", countries);
        model.addAttribute("brands", brands);
        return "admin/brand_tables";
    }

    @PostMapping(value = "/admin/brands/save")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
    public String saveBrand(@RequestParam(name = "add_name", defaultValue = "No Name") String name,
                           @RequestParam(name="add_country_id", defaultValue = "No Description") Long country_id){

        Country cr = brandService.getCountry(country_id);
        itemService.saveBrand(new Brand(name, cr));
        return "redirect:/admin/brands";

    }



    @PostMapping(value = "/admin/brands/update")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
    public String updateBrand(@RequestParam(name = "update_id", defaultValue = "-1") Long id,
                             @RequestParam(name = "update_name", defaultValue = "No Name") String name,
                             @RequestParam(name="update_country_id", defaultValue = "No Description") Long country_id){
        Brand br = itemService.getBrand(id);
        Country cr = brandService.getCountry(country_id);
        if(br != null) {
            itemService.saveBrand(new Brand(id, name, cr));
            return "redirect:/admin/brands";
        }

        return "error";
    }

}
