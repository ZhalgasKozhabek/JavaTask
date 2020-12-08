package kz.springboot.springbootuser.services.impl;

import kz.springboot.springbootuser.entities.Country;
import kz.springboot.springbootuser.repositories.CountryRepository;
import kz.springboot.springbootuser.repositories.ItemRepository;
import kz.springboot.springbootuser.services.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandServiceImpl implements BrandService {
    @Autowired
    private ItemRepository brandRepository;

    @Autowired
    private CountryRepository countryRepository;


    @Override
    public List<Country> getAllCountries() {
        return countryRepository.findAll();
    }

    @Override
    public Country addCountry(Country country) {
        return countryRepository.save(country);
    }

    @Override
    public Country saveCountry(Country country) {
        return countryRepository.save(country);
    }

    @Override
    public Country getCountry(Long id) {
        return countryRepository.getOne(id);
    }
}
