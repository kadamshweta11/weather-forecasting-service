package com.weather.forecasting.controller;

import com.weather.forecasting.model.ForecastResponse;
import com.weather.forecasting.service.ForecastService;
import com.weather.forecasting.model.AddressRequest;

import jakarta.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/forecast")
public class ForecastController {

    private final ForecastService forecastService;
    private static final Logger log = LoggerFactory.getLogger(ForecastController.class);

    public ForecastController(ForecastService forecastService) {
        this.forecastService = forecastService;
    }

    @GetMapping
    public ForecastResponse getForecast(
            @RequestParam(required = false) Double lat,
            @RequestParam(required = false) Double lon,
            @RequestParam(required = false) String country,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String street,
            @RequestParam(required = false) String housenumber,
            HttpServletRequest request) {

        
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        String ip = request.getRemoteAddr();
        String endpoint = request.getRequestURI();
        String timestamp = LocalDateTime.now().toString();

       
        log.info("[{}] Request to '{}' from user='{}', IP='{}'", timestamp, endpoint, username, ip);
        log.info("Params: lat={}, lon={}, country={}, city={}, street={}, housenumber={}",
                lat, lon, country, city, street, housenumber);

       
        if (lat != null && lon != null) {
            log.info("Using coordinates to fetch forecast");
            return forecastService.getForecastByCoordinates(lat, lon);
        } else if (country != null && city != null && street != null && housenumber != null) {
            log.info("Using address to fetch forecast");
            AddressRequest address = new AddressRequest(country, city, street, housenumber);
            return forecastService.getForecastByAddress(address);
        } else {
            log.warn("Bad request: Missing coordinates or address");
            throw new IllegalArgumentException("Please provide either coordinates or full address.");
        }
    }
}
