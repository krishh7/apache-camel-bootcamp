package com.learn.camel.component;

import com.learn.camel.CamelApplication;
import org.apache.camel.ProducerTemplate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(
        classes = CamelApplication.class,
        properties = {"learn.camel.hello.enabled=true"})
public class HelloRouteSpringTest {

    @Autowired
    private ProducerTemplate producerTemplate;

    @Test
    public void testMocksAreValid() {
        System.out.println("Sending 1");
        producerTemplate.sendBody("direct:greeting", "Team");
        System.out.println("Sending 2");
        producerTemplate.sendBody("direct:greeting", "Guys");
    }
}
