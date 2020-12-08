package kz.springboot.springbootuser.controllers;

import kz.springboot.springbootuser.entities.Category;
import kz.springboot.springbootuser.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller(value = "/admin/categories")
public class CategoryController {

    @Autowired
    private ItemService itemService;

    @GetMapping(value = "/admin/categories")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
    public String adminCategories(Model model){
        List<Category> categories = itemService.getAllCategories();
        model.addAttribute("categories", categories);
        return "admin/category_tables";
    }

    @PostMapping(value = "/admin/categories/save")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
    public String saveCategory(@RequestParam(name = "add_name", defaultValue = "No Name") String name,
                               @RequestParam(name="add_logoURL", defaultValue = "") String logoURL){

        itemService.saveCategory(new Category(name, logoURL));
        return "redirect:/admin/categories";

    }



    @PostMapping(value = "/admin/categories/update")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
    public String updateCategory(@RequestParam(name = "update_id", defaultValue = "-1") Long id,
                                 @RequestParam(name = "update_name", defaultValue = "No Name") String name,
                                 @RequestParam(name="update_logoURL", defaultValue = "") String logoURL){
        Category cat = itemService.getCategory(id);
        if(cat != null) {
            itemService.saveCategory(new Category(id, name, logoURL));
            return "redirect:/admin/categories";
        }

        return "error";
    }


}
