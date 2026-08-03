package br.com.whobetter.predictionservice.service;

import br.com.whobetter.predictionservice.client.MatchServiceClient;
import br.com.whobetter.predictionservice.dto.MatchResponse;
import br.com.whobetter.predictionservice.exception.MatchClosedException;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.resilience.annotation.Retryable;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class MatchValidator {

    private final MatchServiceClient matchServiceClient;

    @Retryable(
            includes = FeignException.class,
            excludes = FeignException.NotFound.class,
            maxRetries = 3,
            delay = 1000,
            multiplier = 2.0,
            maxDelay = 10000,
            jitter = 200
    )
    public void validateMatchOpen(UUID matchId, UUID expectedGroupId) {
        MatchResponse match = matchServiceClient.findById(matchId);

        if (!match.groupId().equals(expectedGroupId)) {
            throw new MatchClosedException(matchId, "GROUP_MISMATCH");
        }

        if (!"OPEN".equalsIgnoreCase(match.status())) {
            throw new MatchClosedException(matchId, match.status());
        }
    }
}
