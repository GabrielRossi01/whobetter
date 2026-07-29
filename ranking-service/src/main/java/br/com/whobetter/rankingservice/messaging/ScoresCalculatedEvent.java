package br.com.whobetter.rankingservice.messaging;

import java.util.List;
import java.util.UUID;

public record ScoresCalculatedEvent(
        UUID matchId,
        UUID groupId,
        List<UUID> affectedUserIds
) {}
