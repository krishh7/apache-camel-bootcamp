package com.learn.camel.component.rest;

import com.learn.camel.dto.WeatherDTO;
import lombok.RequiredArgsConstructor;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.support.DefaultMessage;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "learn.camel.rest-java-dsl.enabled", havingValue = "true")
public class RestJavaDSL extends RouteBuilder {

    private final WeatherDataProvider weatherDataProvider;

    // http://localhost:8080/services/javaDsl/weather/{city}
    @Override
    public void configure() throws Exception {
        from("rest:get:javaDsl/weather/{city}?produces=application/json")
                .outputType(WeatherDTO.class)
                .process(this::getWeatherData);
    }

    private void getWeatherData(Exchange exchange) {
        String city = exchange.getMessage().getHeader("city", String.class);
        WeatherDTO currentWeather = weatherDataProvider.getCurrentWeather(city);
        Message message = new DefaultMessage(exchange.getContext());
        message.setBody(currentWeather);
        exchange.setMessage(message);
    }
}
