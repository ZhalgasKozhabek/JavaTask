package kz.springboot.springbootuser.services;

import kz.springboot.springbootuser.entities.Brand;
import kz.springboot.springbootuser.entities.Category;
import kz.springboot.springbootuser.entities.Item;

import java.util.List;

public interface ItemService {
    Item addItem(Item item);
    List<Item> getAllItems();
    Item getItem(Long id);
    void deleteItem(Long id);
    Item saveItem(Item item);

    List<Brand> getAllBrands();
    Brand addBrand(Brand brand);
    Brand saveBrand(Brand country);
    Brand getBrand(Long id);



    List<Category> getAllCategories();
    Category addCategory(Category category);
    Category saveCategory(Category category);
    Category getCategory(Long id);
    List<Category> getCategoriesById(List<Long> categories);

    List<Item> findAllByTypeEquals(String type);
    List<Item> findDistinctByType(String type);
    boolean existsItemByIdEquals(Long id);

    List<Item> findAllByNameContainsOrderByPriceAsc(String name);
    List<Item> findAllByNameContainsOrderByPriceDesc(String name);
    List<Item> findAllByNameContainsAndPriceBetweenOrderByPriceAsc(String name, int price1, int price2);
    List<Item> findAllByNameContainsAndPriceBetweenOrderByPriceDesc(String name, int price1, int price2);


    List<Item> findAllByNameContainsAndPriceBetweenAndBrandEqualsOrderByPriceAsc(String name, int price_from, int price_to, Brand br);
    List<Item> findAllByNameContainsAndPriceBetweenAndBrandEqualsOrderByPriceDesc(String name, int price_from, int price_to, Brand br);

    List<Item> findAllByCategoriesContains(Category cat);
    List<Item> findAllByNameContains(String name);
}