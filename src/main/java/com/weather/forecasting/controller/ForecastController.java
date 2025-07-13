package com.weather.forecasting.controller;

import com.weather.forecasting.model.ForecastResponse;
import com.weather.forecasting.service.ForecastService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/forecast")
public class ForecastController {

    private final ForecastService forecastService;

    public ForecastController(ForecastService forecastService) {
        this.forecastService = forecastService;
    }

    @GetMapping
    public ForecastResponse getForecastByCoordinates(
            @RequestParam double lat,
            @RequestParam double lon) {

        return forecastService.getForecast(lat, lon);
    }
}
