package com.learn.camel.component.rest;

import com.learn.camel.dto.WeatherDTO;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
//@ConditionalOnProperty(name = "learn.camel.rest-java-dsl.enabled", havingValue = "true")
public class WeatherDataProvider {

    private static Map<String, WeatherDTO> weatherData = new HashMap<>();

    public WeatherDataProvider() {
        WeatherDTO weatherDTO =
                WeatherDTO.builder()
                        .city("Hyderabad")
                        .temp("10")
                        .unit("C")
                        .receivedTime(new Date().toString())
                        .id(1)
                        .build();

        weatherData.put("HYDERABAD", weatherDTO);
    }

    public WeatherDTO getCurrentWeather(String city) {
        return weatherData.get(city.toUpperCase());
    }

    public void setCurrentWeather(WeatherDTO weatherDTO) {
        weatherDTO.setCity(new Date().toString());
        weatherData.put(weatherDTO.getCity().toUpperCase(), weatherDTO);
    }
}
