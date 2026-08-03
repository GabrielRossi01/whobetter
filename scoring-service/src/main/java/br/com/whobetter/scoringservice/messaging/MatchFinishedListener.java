package br.com.whobetter.scoringservice.messaging;

import br.com.whobetter.scoringservice.config.RabbitMqConfig;
import br.com.whobetter.scoringservice.service.ScoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class MatchFinishedListener {

    private final ScoreService scoreService;

    @RabbitListener(
            queues = RabbitMqConfig.MATCH_FINISHED_QUEUE,
            containerFactory = "rabbitListenerContainerFactory"
    )
    public void handle(DomainEvent<MatchFinishedEvent> event) {
        log.info("Recebido match.finished evento para matchId={}", event.payload().matchId());
        scoreService.scoreMatch(event.payload().matchId());
    }
}
