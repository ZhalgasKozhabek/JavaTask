package kz.springboot.springbootuser.services.impl;

import kz.springboot.springbootuser.entities.Brand;
import kz.springboot.springbootuser.entities.Category;
import kz.springboot.springbootuser.entities.Item;
import kz.springboot.springbootuser.repositories.BrandRepository;
import kz.springboot.springbootuser.repositories.CategoryRepository;
import kz.springboot.springbootuser.repositories.ItemRepository;
import kz.springboot.springbootuser.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Item addItem(Item item) {
        return itemRepository.save(item);
    }

    @Override
    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    @Override
    public Item getItem(Long id) {
        return itemRepository.getOne(id);
    }

    @Override
    public void deleteItem(Long id) {
        itemRepository.deleteById(id);
    }

    @Override
    public Item saveItem(Item item) {
        return itemRepository.save(item);
    }

    @Override
    public List<Brand> getAllBrands() {
        return brandRepository.findAll();
    }

    @Override
    public Brand addBrand(Brand brand) {
        return brandRepository.save(brand);
    }

    @Override
    public Brand saveBrand(Brand brand) {
        return brandRepository.save(brand);
    }

    @Override
    public Brand getBrand(Long id) {
        return brandRepository.getOne(id);
    }


    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category addCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Category getCategory(Long id) { return categoryRepository.getOne(id); }

    @Override
    public List<Category> getCategoriesById(List<Long> categories) { return categoryRepository.findAllById(categories); }

    @Override
    public List<Item> findAllByTypeEquals(String type) {
        return itemRepository.findAllByTypeEquals(type);
    }

    @Override
    public List<Item> findDistinctByType(String type) {
        return itemRepository.findDistinctByType(type);
    }

    @Override
    public boolean existsItemByIdEquals(Long id) {
        return itemRepository.existsItemByIdEquals(id);
    }


    @Override
    public List<Item> findAllByNameContainsOrderByPriceAsc(String name) {
        return itemRepository.findAllByNameContainsOrderByPriceAsc(name);
    }

    @Override
    public List<Item> findAllByNameContainsOrderByPriceDesc(String name) {
        return itemRepository.findAllByNameContainsOrderByPriceDesc(name);
    }

    @Override
    public List<Item> findAllByNameContainsAndPriceBetweenOrderByPriceAsc(String name, int price1, int price2) {
        return itemRepository.findAllByNameContainsAndPriceBetweenOrderByPriceAsc(name, price1, price2);
    }

    @Override
    public List<Item> findAllByNameContainsAndPriceBetweenOrderByPriceDesc(String name, int price1, int price2) {
        return itemRepository.findAllByNameContainsAndPriceBetweenOrderByPriceDesc(name, price1, price2);
    }

    @Override
    public List<Item> findAllByNameContainsAndPriceBetweenAndBrandEqualsOrderByPriceAsc(String name, int price_from, int price_to, Brand br) {
        return itemRepository.findAllByNameContainsAndPriceBetweenAndBrandEqualsOrderByPriceAsc(name, price_from, price_to, br);
    }

    @Override
    public List<Item> findAllByNameContainsAndPriceBetweenAndBrandEqualsOrderByPriceDesc(String name, int price_from, int price_to, Brand br) {
        return itemRepository.findAllByNameContainsAndPriceBetweenAndBrandEqualsOrderByPriceDesc(name, price_from, price_to, br);
    }


    @Override
    public List<Item> findAllByCategoriesContains(Category cat) {
        return itemRepository.findAllByCategoriesContains(cat);
    }


    @Override
    public List<Item> findAllByNameContains(String name) {
        return itemRepository.findAllByNameContains(name);
    }
}
