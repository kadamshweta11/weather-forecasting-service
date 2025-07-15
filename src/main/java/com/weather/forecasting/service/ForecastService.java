package com.weather.forecasting.service;

import com.weather.forecasting.model.AddressRequest;
import com.weather.forecasting.model.Coordinate;
import com.weather.forecasting.model.ForecastResponse;
import org.springframework.stereotype.Service;

@Service
public class ForecastService {

    private final WeatherService weatherService;
    private final GeocodingService geocodingService;

    public ForecastService(WeatherService weatherService, GeocodingService geocodingService) {
        this.weatherService = weatherService;
        this.geocodingService = geocodingService;
    }

    /**
     * Use coordinates directly (Part 1 compatible)
     */
    public ForecastResponse getForecastByCoordinates(double lat, double lon) {
        Coordinate coordinate = new Coordinate(lat, lon);
        return weatherService.getWeather(coordinate);
    }

    /**
     * Use full address to first geocode, then fetch weather
     */
    public ForecastResponse getForecastByAddress(AddressRequest address) {
        Coordinate coordinate = geocodingService.getCoordinate(address);
        return weatherService.getWeather(coordinate);
    }
}
