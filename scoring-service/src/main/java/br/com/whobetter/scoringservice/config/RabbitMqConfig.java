package br.com.whobetter.scoringservice.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    public static final String WHOBETTER_EXCHANGE = "whobetter.events";
    public static final String WHOBETTER_DLX = "whobetter.events.dlx";

    public static final String MATCH_FINISHED_QUEUE = "scoring.match.finished.queue";
    public static final String MATCH_FINISHED_ROUTING_KEY = "match.finished";
    public static final String MATCH_FINISHED_DLQ = "scoring.match.finished.dlq";

    public static final String SCORES_CALCULATED_QUEUE = "ranking.scores.calculated.queue";
    public static final String SCORES_CALCULATED_ROUTING_KEY = "scores.calculated";

    @Bean
    TopicExchange whobetterExchange() {
        return new TopicExchange(WHOBETTER_EXCHANGE);
    }

    @Bean
    DirectExchange whobetterDlx() {
        return new DirectExchange(WHOBETTER_DLX);
    }

    @Bean
    Queue matchFinishedQueue() {
        return QueueBuilder.durable(MATCH_FINISHED_QUEUE)
                .withArgument("x-dead-letter-exchange", WHOBETTER_DLX)
                .withArgument("x-dead-letter-routing-key", MATCH_FINISHED_DLQ)
                .build();
    }

    @Bean
    Binding matchFinishedBinding() {
        return BindingBuilder
                .bind(matchFinishedQueue())
                .to(whobetterExchange())
                .with(MATCH_FINISHED_ROUTING_KEY);
    }

    @Bean
    Queue matchFinishedDlq() {
        return QueueBuilder.durable(MATCH_FINISHED_DLQ).build();
    }

    @Bean
    Binding matchFinishedDlqBinding() {
        return BindingBuilder
                .bind(matchFinishedDlq())
                .to(whobetterDlx())
                .with(MATCH_FINISHED_DLQ);
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
    MessageConverter jacksonJsonMessageConverter() {
        return new JacksonJsonMessageConverter();
    }

    @Bean
    RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, MessageConverter converter) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(converter);
        return template;
    }

    @Bean
    SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
            ConnectionFactory connectionFactory,
            MessageConverter converter
    ) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(converter);
        factory.setDefaultRequeueRejected(false);
        return factory;
    }
}