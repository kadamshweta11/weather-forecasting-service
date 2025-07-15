package com.weather.forecasting.service;

import com.weather.forecasting.model.AddressRequest;
import com.weather.forecasting.model.Coordinate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.json.JSONArray;
import org.json.JSONObject;


@Service
public class GeocodingService {
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

        // call the API
        String response=restTemplate.getForObject(url,String.class);
        JSONObject json=new JSONObject(response);

        // parse Json
        JSONArray results = json.getJSONArray("results");

if (results.isEmpty()) {
    throw new IllegalArgumentException("Address not found: Geocoding returned no results.");
}

JSONObject location = results.getJSONObject(0).getJSONObject("geometry");

        double lat=location.getDouble("lat");
        double lon=location.getDouble("lng");

        return new Coordinate(lat,lon);
        
    }
}
