package com.learn.camel.component;

import org.apache.camel.builder.AdviceWith;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.model.RouteDefinition;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.junit.jupiter.api.Test;

public class HelloRouteJUnitAdvisedTest extends CamelTestSupport {

    @Override
    protected RouteBuilder createRouteBuilder() {
        return new HelloRoute();
    }

    @Test
    public void testMocksAreValid() throws Exception {
        RouteDefinition routeDefinition = context.getRouteDefinition("greetings");

        AdviceWith.adviceWith(routeDefinition, context,
                new AdviceWithRouteBuilder() {

                    @Override
                    public void configure() throws Exception {
                        weaveAddLast().to("mock:advisedGreeting");
                    }
                });

        context.start();

        MockEndpoint mockEndpoint = getMockEndpoint("mock:advisedGreeting");
        mockEndpoint.setExpectedMessageCount(2);
        template.sendBody("direct:greeting", "Team");
        template.sendBody("direct:greeting", "Guys");
        mockEndpoint.assertIsSatisfied();
    }
}
