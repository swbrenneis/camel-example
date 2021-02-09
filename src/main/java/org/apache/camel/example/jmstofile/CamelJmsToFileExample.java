package org.apache.camel.example.jmstofile;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

import java.util.concurrent.TimeUnit;

public class CamelJmsToFileExample {

    public void test() throws Exception {

        // Create default context
        CamelContext context = new DefaultCamelContext();

        // Set up ActiveMQ JMS components
//        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("vm://localhost?broker.persistent=false");
        // Add a named component
//        context.addComponent("test-jms", JmsComponent.jmsComponentAutoAcknowledge(connectionFactory));
        // Build the route
        context.addRoutes(new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("test-jms:queue:test.queue").to("file://test");
            }
        });

        // Producer template
        ProducerTemplate template = context.createProducerTemplate();

        // Don't forget to start the context
        context.start();

        for (int i = 1; i < 10; i++) {
            template.sendBody("test-jms:queue:test.queue", "TestMessage: " + i);
        }

        TimeUnit.SECONDS.sleep(1);
        context.stop();
    }
}
