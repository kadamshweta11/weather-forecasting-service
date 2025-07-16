package com.weather.forecasting.service;

import com.weather.forecasting.model.AddressRequest;
import com.weather.forecasting.model.Coordinate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.json.JSONArray;
import org.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class GeocodingService {
    private static final Logger log = LoggerFactory.getLogger(GeocodingService.class);
    @Value("${opencage.api.key}")
    private String apiKey;
    private final RestTemplate restTemplate=new RestTemplate();
    public Coordinate getCoordinate(AddressRequest address){
        // convert address to single query string
        String query=String.format("%s,%s,%s,%s",
        address.getCountry(),
        address.getCity(),
        address.getStreet(),
        address.getHousenumber()
        );
        String url=UriComponentsBuilder.fromHttpUrl("https://api.opencagedata.com/geocode/v1/json")
        .queryParam("q",query)
        .queryParam("key",apiKey)
        .build()
        .toUriString();
        log.info("Calling OpenCage API with URL: {}", url);

        // call the API
        String response=restTemplate.getForObject(url,String.class);
        JSONObject json=new JSONObject(response);

        // parse Json
        JSONArray results = json.getJSONArray("results");

if (results.isEmpty()) {
    log.error("No results found for address: {}", address);
    throw new IllegalArgumentException("Address not found: Geocoding returned no results.");
}

JSONObject location = results.getJSONObject(0).getJSONObject("geometry");

        double lat=location.getDouble("lat");
        double lon=location.getDouble("lng");
        log.info("Geocoding result - Latitude: {}, Longitude: {}", lat, lon);
        return new Coordinate(lat,lon);
        
    }
}
