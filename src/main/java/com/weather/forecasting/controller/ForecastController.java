package com.weather.forecasting.controller;

import com.weather.forecasting.model.ForecastResponse;
import com.weather.forecasting.service.ForecastService;
import org.springframework.web.bind.annotation.*;

import com.weather.forecasting.model.AddressRequest;

@RestController
@RequestMapping("/forecast")
public class ForecastController {

    private final ForecastService forecastService;

    public ForecastController(ForecastService forecastService) {
        this.forecastService = forecastService;
    }

    @GetMapping
    public ForecastResponse getForecastByCoordinates(
            // part 1 basic coordinates
            @RequestParam(required = false) Double lat,
            @RequestParam(required = false) Double lon,
            // Part 2=auth
            @RequestParam(required = false) String country,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String street,
            @RequestParam(required = false) String housenumber)
             {
                if(lat !=null && lon !=null){
                    // return coordinates based forecast
                    return forecastService.getForecastByCoordinates(lat,lon);
                }else if( country !=null && city !=null && street!=null &&housenumber!=null){
                    // return address based forecast
                    AddressRequest address=new AddressRequest(country,city,street,housenumber);
                    return forecastService.getForecastByAddress(address);
                }else{
                    throw new IllegalArgumentException("Please provide coordinates of address");
                }

    }
}
