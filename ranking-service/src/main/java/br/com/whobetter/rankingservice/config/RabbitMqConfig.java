package br.com.whobetter.rankingservice.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    public static final String WHOBETTER_EXCHANGE = "whoetter.events";
    public static final String SCORES_CALCULATED_QUEUE = "ranking.scores.calculated.queue";
    public static final String SCORES_CALCULATED_ROUTING_KEY = "scores.calculated";

    @Bean
    TopicExchange whoetterExchange() {
        return new TopicExchange(WHOBETTER_EXCHANGE);
    }

    @Bean
    Queue scoresCalculatedQueue() {
        return QueueBuilder.durable(SCORES_CALCULATED_QUEUE).build();
    }

    @Bean
    Binding scoresCalculatedBinding() {
        return BindingBuilder
                .bind(scoresCalculatedQueue())
                .to(whoetterExchange())
                .with(SCORES_CALCULATED_ROUTING_KEY);
    }

    @Bean
    JacksonJsonMessageConverter jacksonJsonMessageConverter() {
        return new JacksonJsonMessageConverter();
    }
}
