package com.learn.camel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.learn.camel"})
public class CamelApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(CamelApplication.class, args);
    }
}
