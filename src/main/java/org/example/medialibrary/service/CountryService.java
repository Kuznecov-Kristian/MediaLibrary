package org.example.medialibrary.service;

import org.example.medialibrary.entity.Country;
import org.example.medialibrary.repository.CountryRepository;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CountryService {

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    public CountryService(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    // Создание новой страны
    public Country createCountry(String countryName) {
        Country country = new Country(countryName);
        return countryRepository.save(country);
    }

    // Поиск страны по ID
    public Optional<Country> findCountryById(Long id) {
        return countryRepository.findById(id);
    }

    // Поиск страны по названию
    public Optional<Country> findCountryByName(String name) {
        return countryRepository.findByCountry(name);
    }

    // Получение всех стран
    public List<Country> findAllCountries() {
        return countryRepository.findAll();
    }

    public List<String> findAllCountryNames() {
        return countryRepository.findAll()
                .stream()
                .map(Country::getCountry)
                .toList();
    }

    // Обновление страны
    public Country updateCountry(Long id, String newCountryName) {
        Optional<Country> existingCountry = countryRepository.findById(id);
        if (existingCountry.isPresent()) {
            Country country = existingCountry.get();
            country.setCountry(newCountryName);
            return countryRepository.save(country);
        } else {
            throw new RuntimeException("Country with ID " + id + " not found.");
        }
    }

    // Удаление страны
    public void deleteCountry(Long id) {
        if (countryRepository.existsById(id)) {
            countryRepository.deleteById(id);
        } else {
            throw new RuntimeException("Country with ID " + id + " not found.");
        }
    }
}

