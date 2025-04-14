package co.za.techie.sajjaad.service;


import java.util.List;

import org.openapitools.client.model.CountryDetails;

import co.za.techie.sajjaad.model.Country;

public interface CountryService {
	public List<Country> getCountries();
	public CountryDetails getCountryDetails(String countryName);
}
