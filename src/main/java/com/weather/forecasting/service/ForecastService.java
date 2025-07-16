package com.weather.forecasting.service;

import com.weather.forecasting.model.AddressRequest;
import com.weather.forecasting.model.Coordinate;
import com.weather.forecasting.model.ForecastResponse;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Service
public class ForecastService {
    private static final Logger log = LoggerFactory.getLogger(ForecastService.class);

    private final WeatherService weatherService;
    private final GeocodingService geocodingService;

    public ForecastService(WeatherService weatherService, GeocodingService geocodingService) {
        this.weatherService = weatherService;
        this.geocodingService = geocodingService;
    }

   
    public ForecastResponse getForecastByCoordinates(double lat, double lon) {
        log.info("Getting weather forecast using coordinates: lat={}, lon={}", lat, lon);
        Coordinate coordinate = new Coordinate(lat, lon);
        ForecastResponse response = weatherService.getWeather(coordinate);
        log.info("Forecast response: {}", response);

        return response;
    }


    public ForecastResponse getForecastByAddress(AddressRequest address) {
        log.info("Getting coordinates for address: {}", address);
        Coordinate coordinate = geocodingService.getCoordinate(address);
        log.info("Resolved coordinates: lat={}, lon={}", coordinate.getLat(), coordinate.getLon());
        ForecastResponse response = weatherService.getWeather(coordinate);
        log.info("Forecast response: {}", response);
        return response;
    }

    public ForecastResponse getForecast(double lat, double lon) {
        
        throw new UnsupportedOperationException("Unimplemented method 'getForecast'");
    }
}
