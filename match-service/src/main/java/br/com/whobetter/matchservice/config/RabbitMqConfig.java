package br.com.whobetter.matchservice.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.JacksonJsonMessageConverter;

@Configuration
public class RabbitMqConfig {

    public static final String WHOBETTER_EXCHANGE = "whobetter.events";
    public static final String MATCH_FINISHED_QUEUE = "scoring.match.finished.queue";
    public static final String MATCH_FINISHED_ROUTING_KEY = "match.finished";

    @Bean
    Queue matchFinishedQueue() {
        return QueueBuilder.durable(MATCH_FINISHED_QUEUE).build();
    }

    @Bean
    TopicExchange whobetterExchange() {
        return new TopicExchange(WHOBETTER_EXCHANGE);
    }

    @Bean
    Binding matchFinishedBinding() {
        return BindingBuilder
                .bind(matchFinishedQueue())
                .to(whobetterExchange())
                .with(MATCH_FINISHED_ROUTING_KEY);
    }

    @Bean
    JacksonJsonMessageConverter jacksonJsonMessageConverter() {
        return new JacksonJsonMessageConverter();
    }
}
