package kz.springboot.springbootuser.services;


import kz.springboot.springbootuser.entities.Country;

import java.util.List;

public interface BrandService {
    List<Country> getAllCountries();
    Country addCountry(Country country);
    Country saveCountry(Country country);
    Country getCountry(Long id);

}