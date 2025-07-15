package com.weather.forecasting.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressRequest {
    private String country;
    private String city;
    private String Street;
    private String housenumber;
}
