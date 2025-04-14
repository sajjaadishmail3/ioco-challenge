package co.za.techie.sajjaad.controller;

import java.util.List;

import org.openapitools.client.model.CountryDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.za.techie.sajjaad.service.CountryService;

@RestController
@RequestMapping("/api/countries")
public class CountryController {
	private final CountryService countryService;

    @Autowired
    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping
    public ResponseEntity<co.za.techie.sajjaad.model.Country> getCountries() {
        List<co.za.techie.sajjaad.model.Country> countries = countryService.getCountries();
        ResponseEntity responseEntity = ResponseEntity.ok(countries);
        return responseEntity;
    }
    
    @GetMapping("/{name}")
    public CountryDetails getCountryDetails(@PathVariable String name) {
        return countryService.getCountryDetails(name);
    }

}
