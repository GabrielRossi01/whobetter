package br.com.whobetter.rankingservice.messaging;

import java.time.LocalDateTime;
import java.util.UUID;

public record DomainEvent<T>(
        UUID eventId,
        String eventType,
        LocalDateTime occurredAt,
        String source,
        T payload
) {}
