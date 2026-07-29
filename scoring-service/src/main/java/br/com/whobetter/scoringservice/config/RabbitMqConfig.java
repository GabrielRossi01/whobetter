package br.com.whobetter.scoringservice.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.JacksonJsonMessageConverter;

@Configuration
public class RabbitMqConfig {

    public static final String WHOBETTER_EXCHANGE = "whobetter.events";
    public static final String MATCH_FINISHED_QUEUE = "scoring.match.finished.queue";
    public static final String MATCH_FINISHED_ROUTING_KEY = "match.finished";
    public static final String SCORES_CALCULATED_QUEUE = "ranking.scores.calculated.queue";
    public static final String SCORES_CALCULATED_ROUTING_KEY = "scores.calculated";

    @Bean
    TopicExchange whobetterExchange() {
        return new TopicExchange(WHOBETTER_EXCHANGE);
    }

    @Bean
    Queue matchFinishedQueue() {
        return QueueBuilder.durable(MATCH_FINISHED_QUEUE).build();
    }

    @Bean
    Binding matchFinishedBinding() {
        return BindingBuilder
                .bind(matchFinishedQueue())
                .to(whobetterExchange())
                .with(MATCH_FINISHED_ROUTING_KEY);
    }

    @Bean
    Queue scoresCalculatedQueue() {
        return QueueBuilder.durable(SCORES_CALCULATED_QUEUE).build();
    }

    @Bean
    Binding scoresCalculatedBinding() {
        return BindingBuilder
                .bind(scoresCalculatedQueue())
                .to(whobetterExchange())
                .with(SCORES_CALCULATED_ROUTING_KEY);
    }

    @Bean
    JacksonJsonMessageConverter  jacksonJsonMessageConverter() {
        return new JacksonJsonMessageConverter();
    }
}
