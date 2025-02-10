package com.learn.camel.component;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.apache.camel.test.spring.junit5.MockEndpoints;
import org.junit.jupiter.api.Test;

@MockEndpoints
public class HelloRouteJUnitMockTest extends CamelTestSupport {

    @Override
    protected RouteBuilder createRouteBuilder() {
        return new RouteBuilder() {
            @Override
            public void configure() {
                from("direct:greeting")
                        .to("mock:mockedGreeting");
            }
        };
    }

    @Test
    public void TestMockEndpointsAreValid() throws InterruptedException {
        MockEndpoint mockEndpoint = getMockEndpoint("mock:mockedGreeting");
        mockEndpoint.setExpectedMessageCount(2);

        System.out.println("Sending 1");
        template.sendBody("direct:greeting", "Team");

        System.out.println("Sending 2");
        template.sendBody("direct:greeting", "Guys");

        mockEndpoint.assertIsSatisfied();
    }
}
