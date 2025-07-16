package com.weather.forecasting.service;

import com.weather.forecasting.model.Coordinate;
import com.weather.forecasting.model.ForecastResponse;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Service
public class WeatherService {
    private static final Logger log = LoggerFactory.getLogger(WeatherService.class);

    @Value("${openweathermap.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    public ForecastResponse getWeather(Coordinate coordinate) {
        String url = UriComponentsBuilder
                .fromHttpUrl("https://api.openweathermap.org/data/2.5/forecast")
                .queryParam("lat", coordinate.getLat())
                .queryParam("lon", coordinate.getLon())
                .queryParam("appid", apiKey)
                .queryParam("units", "metric") // Celsius
                .build()
                .toUriString();
        log.info("Calling OpenWeatherMap API with URL: {}", url);
        // Call API
        String response = restTemplate.getForObject(url, String.class);

        // Parse the JSON
        JSONObject json = new JSONObject(response);
        JSONArray forecastList = json.getJSONArray("list");
        JSONObject firstForecast = forecastList.getJSONObject(0);
        JSONObject main = firstForecast.getJSONObject("main");

        long timestamp = firstForecast.getLong("dt");
        double temp = main.getDouble("temp");
        double feelsLike = main.getDouble("feels_like");
        int humidity = main.getInt("humidity");

        long time = firstForecast.getLong("dt"); // UTC timestamp
        log.info("Weather fetched: time={}, temp={}, feels_like={}, humidity={}",
                timestamp, temp, feelsLike, humidity);
        return new ForecastResponse(timestamp, temp, feelsLike, humidity);
    }
}
