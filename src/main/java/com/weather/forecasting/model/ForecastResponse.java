package com.weather.forecasting.model;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class ForecastResponse{
    private long time;
    private double temp;
    private double feels_like;
    private int humidity;
}