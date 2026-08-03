package br.com.whobetter.rankingservice.config;

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

    public static final String SCORES_CALCULATED_QUEUE = "ranking.scores.calculated.queue";
    public static final String SCORES_CALCULATED_ROUTING_KEY = "scores.calculated";
    public static final String SCORES_CALCULATED_DLQ = "ranking.scores.calculated.dlq";

    @Bean
    TopicExchange whobetterExchange() {
        return new TopicExchange(WHOBETTER_EXCHANGE);
    }

    @Bean
    DirectExchange whobetterDlx() {
        return new DirectExchange(WHOBETTER_DLX);
    }

    @Bean
    Queue scoresCalculatedQueue() {
        return QueueBuilder.durable(SCORES_CALCULATED_QUEUE)
                .withArgument("x-dead-letter-exchange", WHOBETTER_DLX)
                .withArgument("x-dead-letter-routing-key", SCORES_CALCULATED_DLQ)
                .build();
    }

    @Bean
    Binding scoresCalculatedBinding() {
        return BindingBuilder
                .bind(scoresCalculatedQueue())
                .to(whobetterExchange())
                .with(SCORES_CALCULATED_ROUTING_KEY);
    }

    @Bean
    Queue scoresCalculatedDlq() {
        return QueueBuilder.durable(SCORES_CALCULATED_DLQ).build();
    }

    @Bean
    Binding scoresCalculatedDlqBinding() {
        return BindingBuilder
                .bind(scoresCalculatedDlq())
                .to(whobetterDlx())
                .with(SCORES_CALCULATED_DLQ);
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