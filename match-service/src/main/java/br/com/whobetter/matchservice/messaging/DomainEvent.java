package br.com.whobetter.matchservice.messaging;

import java.time.LocalDateTime;
import java.util.UUID;

public record DomainEvent<T>(
        UUID eventId,
        String eventType,
        LocalDateTime occurredAt,
        String source,
        T payload
) {
    public static <T> DomainEvent<T> of(String eventType, String source, T payload) {
        return new DomainEvent<>(
                UUID.randomUUID(),
                eventType,
                LocalDateTime.now(),
                source,
                payload
        );
    }
}
