package com.weather.forecasting.service;
import com.weather.forecasting.model.ForecastResponse;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Random;

@Service
 public class ForecastService{
    private final Random random=new Random();
    public ForecastResponse getForecast(double lat,double lon){
        long time=Instant.now().getEpochSecond();
        double  temp=10+(20-10)*random.nextDouble();
        double feelsLike=temp-1.5;
        int humidity=40+ random.nextInt(60);
        return new ForecastResponse(time,round(temp),round(feelsLike),humidity);
    }
    private double round(double value){
        return Math.round(value*100.0)/100.0;
    }
 }