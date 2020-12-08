package kz.springboot.springbootuser.controllers;

import kz.springboot.springbootuser.entities.Brand;
import kz.springboot.springbootuser.entities.Category;
import kz.springboot.springbootuser.entities.Item;
import kz.springboot.springbootuser.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller(value = "/admin/items")
public class ItemController {

    @Autowired
    private ItemService itemService;


    @GetMapping(value = "/admin/items")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
    public String adminItems(Model model){
        List<Item> items = itemService.getAllItems();
        model.addAttribute("items", items);
        return "admin/item_tables";
    }


    @GetMapping(value = "/admin/items/detail/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
    public String adminItemDetail(Model model, @PathVariable Long id){
        Item item = itemService.getItem(id);
        System.out.println("Item id = "  + item.getId());
        List<Brand> brands = itemService.getAllBrands();
        List<Category> categories = itemService.getAllCategories();

        for(Category cat1: item.getCategories())
            categories.removeIf(cat2 -> cat1.getId().equals(cat2.getId()));


        model.addAttribute("item", item);
        model.addAttribute("brands", brands);
        model.addAttribute("categories", categories);
        return "admin/itemDetailAdmin";

    }

    @GetMapping(value="/admin/items/add")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
    public String addItemPage(Model model){
        List<Brand> brands = itemService.getAllBrands();
        List<Category> categories = itemService.getAllCategories();

        model.addAttribute("brands", brands);
        model.addAttribute("categories", categories);
        return "/admin/itemAdd";
    }

    @PostMapping(value="/admin/items/add")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
    public String addItem(@RequestParam(name = "add_name", defaultValue = "No Name") String name,
                          @RequestParam(name="add_description", defaultValue = "No Description") String description,
                          @RequestParam(name="add_type", defaultValue = "")  String type,
                          @RequestParam(name="add_price", defaultValue = "1")  int price,
                          @RequestParam(name="add_amount", defaultValue = "1")  int amount,
                          @RequestParam(name="add_stars", defaultValue = "1")  int stars,
                          @RequestParam(name="add_picture_url", defaultValue = "No URL") String picture_url,
                          @RequestParam(name="add_top_page", defaultValue = "false") boolean add_top_page,
                          @RequestParam(name="add_brand", defaultValue = "-1") Long brand_id,
                          @RequestParam(name="categories") List<Long> categories){

        Brand br = itemService.getBrand(brand_id);
        if(br != null) {
            Item item = itemService.addItem(new Item(name, description, type, price, amount, stars, picture_url, picture_url, add_top_page, br));
            item.setCategories(itemService.getCategoriesById(categories));
            itemService.saveItem(item);
        }
        return "redirect:/admin/items";
    }

    @PostMapping(value="/admin/items/update")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
    public String updateItem(@RequestParam(name = "id", defaultValue = "-1") Long id,
                             @RequestParam(name = "update_name", defaultValue = "No Name") String name,
                             @RequestParam(name="update_description", defaultValue = "No Description") String description,
                             @RequestParam(name="update_type", defaultValue = "")  String type,
                             @RequestParam(name="update_price", defaultValue = "1")  int price,
                             @RequestParam(name="update_amount", defaultValue = "1")  int amount,
                             @RequestParam(name="update_stars", defaultValue = "1")  int stars,
                             @RequestParam(name="update_small_picture_url", defaultValue = "No URL") String small_picture_url,
                             @RequestParam(name="update_large_picture_url", defaultValue = "No URL") String large_picture_url,
                             @RequestParam(name="update_top_page", defaultValue = "false") boolean add_top_page,
                             @RequestParam(name="update_brand", defaultValue = "-1") Long brand_id){
        Brand br = itemService.getBrand(brand_id);
        if(itemService.existsItemByIdEquals(id)) {
            itemService.saveItem(new Item(id, name, description, type, price, amount, stars, small_picture_url, large_picture_url, add_top_page, br));
            return "redirect:/admin/items";
        }
        return "error";
    }


    @PostMapping(value="/admin/items/delete")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
    public String deleteItem(@RequestParam(name = "id", defaultValue = "-1") Long id){
        if(itemService.existsItemByIdEquals(id)) {
            itemService.deleteItem(id);
            return "redirect:/admin/items";
        }
        return "error";
    }


    @PostMapping(value="/admin/items/assignCategory")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
    public String assignCategory(@RequestParam(name = "item_id", defaultValue = "-1") Long item_id,
                             @RequestParam(name = "category_id", defaultValue = "-1") Long category_id){

        Category cat = itemService.getCategory(category_id);
        System.out.println("I am here");
        if(cat != null) {
            System.out.println("cat = " + cat);
            Item item = itemService.getItem(item_id);
            if (item != null) {
                System.out.println("item = " + item);

                List<Category> categories = item.getCategories();

                if(categories == null)
                    categories = new ArrayList<>();

                categories.add(cat);
                item.setCategories(categories);
                itemService.saveItem(item);
                return "redirect:/admin/items/detail/" + item_id;
            }
        }

        return "error";
    }


    @PostMapping(value="/admin/items/revokeCategory")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
    public String revokeCategory(@RequestParam(name = "item_id", defaultValue = "-1") Long item_id,
                             @RequestParam(name = "category_id", defaultValue = "-1") Long category_id){

        Category cat = itemService.getCategory(category_id);
        if(cat != null) {
            Item item = itemService.getItem(item_id);
            if (item != null) {
                List<Category> categories = item.getCategories();

                if(categories == null)
                    categories = new ArrayList<>();

                categories.remove(cat);
                item.setCategories(categories);
                itemService.saveItem(item);
                return "redirect:/admin/items/detail/" + item_id;
            }
        }

        return "error";
    }
}
