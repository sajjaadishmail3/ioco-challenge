package co.za.techie.sajjaad.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.openapitools.client.model.CountryDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import co.za.techie.sajjaad.model.Country;
import co.za.techie.sajjaad.service.CountryService;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CountryServiceImpl implements CountryService {
	private final RestTemplate restTemplate;
	private List<Country> countries; 

	public CountryServiceImpl(RestTemplate restTemplate) {
		super();
		this.restTemplate = restTemplate;
	}

	@Override
	public List<Country> getCountries() {
		String apiUrl = "https://restcountries.com/v3.1/all";
        List<Map<String, Object>> countryList = new ArrayList<Map<String,Object>>();

		try {
			countryList =  restTemplate.getForObject(apiUrl, List.class);
			
			if (countryList.size() > 0) {
				log.info("Retieving list of countries: {}", countryList);
			} else {
				throw new Exception("Unable to retrieve the list of countries!");
			}
			
		} catch (Exception e) {
			log.error("Error: {}", e.getMessage());
		}

		this.countries = countryList.stream().map(c -> new Country(
                ((Map<String, String>) c.get("name")).get("common"),
                c.get("capital") != null ? ((List<String>) c.get("capital")).get(0) : "Unknown",
                ((Number) c.get("population")).longValue(),
                ((Map<String, String>) c.get("flags")).get("png")
        )).collect(Collectors.toList());

		return countries;
	}

	@Override
	public CountryDetails getCountryDetails(String countryName) {
		CountryDetails details = new CountryDetails();
		Country country = countries.stream()
				.filter(c -> c.getName().contains(countryName))
				.findAny().get();
		
		try {
			if (country.equals(null)) {
				throw new Exception("Error getting cuntry details for " + countryName);
			} else {
				log.info("Successfully routed to {} details", countryName);
			}

			
			details.setCapital(country.getCapital());
			details.setName(countryName);
			details.setFlag(country.getFlagUrl());
			details.setPopulation((int)country.getPopulation());
		} catch (Exception e) {
			log.error("Error getting cuntry details for {}", countryName);
		}
		
		return details;
	}

}
