package com.learn.camel.component;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import static org.apache.camel.LoggingLevel.INFO;

@Component
@ConditionalOnProperty(name = "learn.camel.hello.enabled", havingValue = "true")
public class HelloRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("direct:greeting")
                .id("greetings")
                .log(INFO, "Hello ${body}")
                .choice()
                    .when()
                        .simple("${body} contains 'Team'")
                        .log(INFO, "I would like to work with Teams")
                .otherwise()
                .log(INFO, "Solo Fighter :)")
                .end()
                .to("direct:finishGreeting");

        from("direct:finishGreeting").log(INFO, "Bye ${body}");
    }
}
