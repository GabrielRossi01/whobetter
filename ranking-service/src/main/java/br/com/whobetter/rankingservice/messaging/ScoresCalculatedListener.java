package br.com.whobetter.rankingservice.messaging;

import br.com.whobetter.rankingservice.config.RabbitMqConfig;
import br.com.whobetter.rankingservice.service.RankingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class ScoresCalculatedListener {

    private final RankingService rankingService;

    @RabbitListener(queues = RabbitMqConfig.SCORES_CALCULATED_QUEUE)
    public void handle(DomainEvent<ScoresCalculatedEvent> event) {
        UUID groupId = event.payload().groupId();
        log.info("Recebido scores.calculated evento para groupId={}", groupId);

        try {
            rankingService.refreshRanking(groupId);
        } catch (Exception ex) {
            log.error("Falha ao recarregar ranking com groupId={}: {}", groupId, ex.getMessage(), ex);
        }
    }
}
