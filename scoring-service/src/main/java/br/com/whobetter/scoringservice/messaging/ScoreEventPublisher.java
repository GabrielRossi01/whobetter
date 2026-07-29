package br.com.whobetter.scoringservice.messaging;

import br.com.whobetter.scoringservice.config.RabbitMqConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ScoreEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    public void publishScoresCalculated(ScoresCalculatedEvent event) {
        DomainEvent<ScoresCalculatedEvent> domainEvent = DomainEvent.of("scores.calculated", "scoring-service", event);

        rabbitTemplate.convertAndSend(
                RabbitMqConfig.WHOBETTER_EXCHANGE,
                RabbitMqConfig.SCORES_CALCULATED_ROUTING_KEY,
                domainEvent
        );
    }
}
