package com.learn.camel.component.rest;

import com.learn.camel.dto.WeatherDTO;
import lombok.RequiredArgsConstructor;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.apache.camel.support.DefaultMessage;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import static org.apache.camel.component.http.HttpConstants.HTTP_RESPONSE_CODE;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Component
@RequiredArgsConstructor
public class RestDSL extends RouteBuilder {

    private final WeatherDataProvider weatherDataProvider;

    private final ConcurrentHashMap<String, WeatherDTO> weatherStorage = new ConcurrentHashMap<>();


    @Override
    public void configure() throws Exception {

        //For Json Pretty
        restConfiguration()
                .component("servlet")
                .bindingMode(RestBindingMode.auto);

        rest()
                .consumes(MediaType.APPLICATION_JSON_VALUE)
                .produces(MediaType.APPLICATION_JSON_VALUE)
                .get("/weather/{city}")
                .outType(WeatherDTO.class)
                .to("direct:getWeatherData")
                    .post("weather")
                    .type(WeatherDTO.class)
                    .to("direct:saveWeatherData");

        from("direct:getWeatherData")
                .process(this::getWeatherData);

        from("direct:saveWeatherData")
                .process(this::saveWeatherDataAndSetToExchange);
    }

    // Save the weather data to in-memory storage
    private void saveWeatherDataAndSetToExchange(Exchange exchange) {
        WeatherDTO dto = exchange.getMessage().getBody(WeatherDTO.class);
        if (dto != null && dto.getCity() != null) {
            weatherStorage.put(dto.getCity().toLowerCase(), dto);
        }
    }


    // Retrieve stored weather data
    private void getWeatherData(Exchange exchange) {
        String city = exchange.getMessage().getHeader("city", String.class);
        WeatherDTO currentWeather = weatherStorage.getOrDefault(city.toLowerCase(), null);

        if (currentWeather != null) {
            Message message = new DefaultMessage(exchange.getContext());
            message.setBody(currentWeather);
            exchange.setMessage(message);
        } else {
            exchange.getMessage().setHeader(HTTP_RESPONSE_CODE, NOT_FOUND.value());
        }
    }

}
