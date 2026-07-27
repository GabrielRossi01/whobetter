package br.com.whobetter.matchservice.messaging;

import br.com.whobetter.matchservice.config.RabbitMqConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MatchEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    public void publishMatchFinished(MatchFinishedEvent event) {
        DomainEvent<MatchFinishedEvent> domainEvent = DomainEvent.of("match.finished", "match-service", event);

        rabbitTemplate.convertAndSend(
                RabbitMqConfig.WHOBETTER_EXCHANGE,
                RabbitMqConfig.MATCH_FINISHED_ROUTING_KEY,
                domainEvent
        );
    }
}
