package co.za.techie.sajjaad.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import co.za.techie.sajjaad.model.Country;
import co.za.techie.sajjaad.service.impl.CountryServiceImpl;

import org.openapitools.client.model.CountryDetails;

@ExtendWith(MockitoExtension.class)
class CountryServiceImplTest {

	@Mock
	private RestTemplate restTemplate;

	@InjectMocks
	private CountryServiceImpl countryService;

	private List<Map<String, Object>> mockApiResponse;

	@BeforeEach
	void setUp() {
		Map<String, Object> country1 = new HashMap<>();
		Map<String, String> name1 = new HashMap<>();
		name1.put("common", "TestCountry1");
		country1.put("name", name1);
		country1.put("capital", Collections.singletonList("Capital1"));
		country1.put("population", 1000000);
		Map<String, String> flags1 = new HashMap<>();
		flags1.put("png", "flag1.png");
		country1.put("flags", flags1);

		Map<String, Object> country2 = new HashMap<>();
		Map<String, String> name2 = new HashMap<>();
		name2.put("common", "TestCountry2");
		country2.put("name", name2);
		country2.put("capital", Collections.singletonList("Capital2"));
		country2.put("population", 2000000);
		Map<String, String> flags2 = new HashMap<>();
		flags2.put("png", "flag2.png");
		country2.put("flags", flags2);

		mockApiResponse = Arrays.asList(country1, country2);
	}

	@Test
	void getCountries_ShouldReturnCountries_WhenApiCallSucceeds() {
		when(restTemplate.getForObject(anyString(), eq(List.class))).thenReturn(mockApiResponse);

		List<Country> result = countryService.getCountries();

		assertNotNull(result);
		assertEquals(2, result.size());

		Country firstCountry = result.get(0);
		assertEquals("TestCountry1", firstCountry.getName());
		assertEquals("Capital1", firstCountry.getCapital());
		assertEquals(1000000L, firstCountry.getPopulation());
		assertEquals("flag1.png", firstCountry.getFlagUrl());

		verify(restTemplate).getForObject(anyString(), eq(List.class));
	}

	@Test
	void getCountries_ShouldHandleEmptyResponse() {
		when(restTemplate.getForObject(anyString(), eq(List.class))).thenReturn(Collections.emptyList());

		List<Country> result = countryService.getCountries();

		assertNotNull(result);
		assertTrue(result.isEmpty());
	}

	@Test
	void getCountries_ShouldHandleApiError() {
		when(restTemplate.getForObject(anyString(), eq(List.class))).thenThrow(new RuntimeException("API Error"));

		List<Country> result = countryService.getCountries();

		assertNotNull(result);
		assertTrue(result.isEmpty());
	}

	@Test
	void getCountryDetails_ShouldReturnDetails_WhenCountryExists() {
		when(restTemplate.getForObject(anyString(), eq(List.class))).thenReturn(mockApiResponse);
		countryService.getCountries();

		CountryDetails result = countryService.getCountryDetails("TestCountry1");

		assertNotNull(result);
		assertEquals("TestCountry1", result.getName());
		assertEquals("Capital1", result.getCapital());
		assertEquals(1000000, result.getPopulation());
		assertEquals("flag1.png", result.getFlag());
	}

	@Test
	void getCountryDetails_ShouldHandleMissingCountry() {
		when(restTemplate.getForObject(anyString(), eq(List.class))).thenReturn(mockApiResponse);
		countryService.getCountries();

		assertThrows(RuntimeException.class, () -> {
			countryService.getCountryDetails("NonExistentCountry");
		});
	}

	@Test
	void getCountryDetails_ShouldHandleNullCountryList() {
		assertThrows(NullPointerException.class, () -> {
			countryService.getCountryDetails("AnyCountry");
		});
	}
}