package com.weather.forecasting.model;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Coordinate {
    private double lat;
    private double lon;
}
