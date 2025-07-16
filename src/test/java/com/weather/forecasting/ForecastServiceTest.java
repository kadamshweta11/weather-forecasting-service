package com.weather.forecasting;

import com.weather.forecasting.model.AddressRequest;
import com.weather.forecasting.model.Coordinate;
import com.weather.forecasting.model.ForecastResponse;
import com.weather.forecasting.service.ForecastService;
import com.weather.forecasting.service.GeocodingService;
import com.weather.forecasting.service.WeatherService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;


public class ForecastServiceTest {

    private ForecastService forecastService;
    private WeatherService weatherService;
    private GeocodingService geocodingService;

    @BeforeEach
    void setup() {
        weatherService = mock(WeatherService.class);
        geocodingService = mock(GeocodingService.class);
        forecastService = new ForecastService(weatherService, geocodingService);
    }

    @Test
    void testGetForecastByCoordinates() {
        Coordinate coord = new Coordinate(52.52, 13.405);
        ForecastResponse expected = new ForecastResponse(123, 20.0, 19.0, 80);

        when(weatherService.getWeather(coord)).thenReturn(expected);

        ForecastResponse result = forecastService.getForecastByCoordinates(52.52, 13.405);
        assertEquals(expected, result);
    }

    @Test
    void testGetForecastByAddress() {
        AddressRequest address = new AddressRequest("Germany", "Berlin", "Street", "10");
        Coordinate coord = new Coordinate(52.5, 13.4);
        ForecastResponse expected = new ForecastResponse(123, 22.0, 21.0, 75);

        when(geocodingService.getCoordinate(address)).thenReturn(coord);
        when(weatherService.getWeather(coord)).thenReturn(expected);

        ForecastResponse result = forecastService.getForecastByAddress(address);
        assertEquals(expected, result);
    }
}
