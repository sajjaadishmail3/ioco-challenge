package co.za.techie.sajjaad.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import co.za.techie.sajjaad.model.Country;
import co.za.techie.sajjaad.service.CountryService;
import org.openapitools.client.model.CountryDetails;

@ExtendWith(MockitoExtension.class)
class CountryControllerTest {

    @Mock
    private CountryService countryService;

    @InjectMocks
    private CountryController countryController;

    private List<Country> mockCountries;
    private CountryDetails mockCountryDetails;

    @BeforeEach
    void setUp() {
        // Setup mock data
        mockCountries = Arrays.asList(
            new Country("TestCountry1", "Capital1", 1000000L, "flag1.png"),
            new Country("TestCountry2", "Capital2", 2000000L, "flag2.png")
        );

        mockCountryDetails = new CountryDetails();
        mockCountryDetails.setName("TestCountry1");
        mockCountryDetails.setCapital("Capital1");
        mockCountryDetails.setPopulation(1000000);
        mockCountryDetails.setFlag("flag1.png");
    }

    @Test
    void getCountries_ShouldReturnListOfCountries() {
        // Arrange
        when(countryService.getCountries()).thenReturn(mockCountries);

        // Act
        ResponseEntity<Country> response = countryController.getCountries();

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        verify(countryService).getCountries();
    }

    @Test
    void getCountries_ShouldReturnEmptyList_WhenNoCountriesExist() {
        // Arrange
        when(countryService.getCountries()).thenReturn(List.of());

        // Act
        ResponseEntity<Country> response = countryController.getCountries();

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertTrue(Objects.nonNull(response.getBody()));
        verify(countryService).getCountries();
    }

    @Test
    void getCountryDetails_ShouldReturnCountryDetails() {
        // Arrange
        String countryName = "TestCountry1";
        when(countryService.getCountryDetails(countryName)).thenReturn(mockCountryDetails);

        // Act
        CountryDetails result = countryController.getCountryDetails(countryName);

        // Assert
        assertNotNull(result);
        assertEquals("TestCountry1", result.getName());
        assertEquals("Capital1", result.getCapital());
        assertEquals(1000000, result.getPopulation());
        verify(countryService).getCountryDetails(countryName);
    }

    @Test
    void getCountryDetails_ShouldThrowException_WhenCountryNotFound() {
        // Arrange
        String countryName = "NonExistentCountry";
        when(countryService.getCountryDetails(countryName))
            .thenThrow(new RuntimeException("Country not found"));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            countryController.getCountryDetails(countryName);
        });
        verify(countryService).getCountryDetails(countryName);
    }

    @Test
    void constructor_ShouldInitializeService() {
        // This test verifies the controller is properly initialized
        CountryService mockService = mock(CountryService.class);
        CountryController controller = new CountryController(mockService);
        assertNotNull(controller);
    }
}