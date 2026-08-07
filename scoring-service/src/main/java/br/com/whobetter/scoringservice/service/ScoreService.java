package br.com.whobetter.scoringservice.service;

import br.com.whobetter.scoringservice.client.MatchServiceClient;
import br.com.whobetter.scoringservice.client.PredictionServiceClient;
import br.com.whobetter.scoringservice.domain.Score;
import br.com.whobetter.scoringservice.domain.ScoringType;
import br.com.whobetter.scoringservice.dto.MatchResponse;
import br.com.whobetter.scoringservice.dto.PredictionResponse;
import br.com.whobetter.scoringservice.exception.MatchNotFinishedException;
import br.com.whobetter.scoringservice.exception.ScoreAlreadyCalculatedException;
import br.com.whobetter.scoringservice.exception.ScoreNotFoundException;
import br.com.whobetter.scoringservice.messaging.ScoreEventPublisher;
import br.com.whobetter.scoringservice.messaging.ScoresCalculatedEvent;
import br.com.whobetter.scoringservice.repository.ScoreRepository;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.resilience.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ScoreService {

    private final ScoreRepository scoreRepository;
    private final MatchServiceClient matchServiceClient;
    private final PredictionServiceClient predictionServiceClient;
    private final ScoreEventPublisher scoreEventPublisher;

    @Retryable(
            includes = FeignException.class,
            excludes = FeignException.NotFound.class,
            maxRetries = 3,
            delay = 1000,
            multiplier = 2.0,
            maxDelay = 10000,
            jitter = 200
    )
    @Transactional
    @PreAuthorize("hasAuthority('SCOPE_scores:calculate')")
    public List<Score> scoreMatch(UUID matchId) {
        MatchResponse match = matchServiceClient.findById(matchId);

        validateFinishedMatch(matchId, match);

        if (scoreRepository.existsByMatchId(matchId)) {
            throw new ScoreAlreadyCalculatedException(matchId);
        }

        List<PredictionResponse> predictions =
                predictionServiceClient.findByMatchId(matchId);

        List<Score> scores = predictions.stream()
                .map(prediction -> createScore(matchId, match, prediction))
                .toList();

        List<Score> savedScores = scoreRepository.saveAll(scores);

        List<UUID> affectedUserIds = savedScores.stream()
                .map(Score::getUserId)
                .distinct()
                .toList();

        scoreEventPublisher.publishScoresCalculated(
                new ScoresCalculatedEvent(
                        matchId,
                        match.groupId(),
                        affectedUserIds
                )
        );

        return savedScores;
    }

    @PreAuthorize("hasAuthority('SCOPE_scores:read')")
    public Score findById(UUID id) {
        return scoreRepository.findById(id)
                .orElseThrow(() -> new ScoreNotFoundException(id));
    }

    @PreAuthorize("hasAuthority('SCOPE_scores:read')")
    public List<Score> findByMatchId(UUID matchId) {
        return scoreRepository.findByMatchId(matchId);
    }

    @PreAuthorize("hasAuthority('SCOPE_scores:read')")
    public List<Score> findByGroupId(UUID groupId) {
        return scoreRepository.findByGroupId(groupId);
    }

    @PreAuthorize("hasAuthority('SCOPE_scores:read')")
    public List<Score> findByUserId(UUID userId) {
        UUID authenticatedUserId = currentUserId();

        if (!authenticatedUserId.equals(userId)) {
            throw new AccessDeniedException(
                    "O usuário não pode consultar scores de outro usuário"
            );
        }

        return scoreRepository.findByUserId(userId);
    }

    private Score createScore(
            UUID matchId,
            MatchResponse match,
            PredictionResponse prediction
    ) {
        ScoringType scoringType = calculateScoringType(
                prediction.predictedHomeScore(),
                prediction.predictedAwayScore(),
                match.homeScore(),
                match.awayScore()
        );

        int points = switch (scoringType) {
            case EXACT_SCORE -> 3;
            case OUTCOME_ONLY -> 1;
            case MISS -> 0;
        };

        return new Score(
                matchId,
                prediction.groupId(),
                prediction.userId(),
                prediction.id(),
                points,
                scoringType
        );
    }

    private void validateFinishedMatch(
            UUID matchId,
            MatchResponse match
    ) {
        if (!"FINISHED".equalsIgnoreCase(match.status())) {
            throw new MatchNotFinishedException(
                    matchId,
                    match.status()
            );
        }

        if (match.homeScore() == null || match.awayScore() == null) {
            throw new MatchNotFinishedException(
                    matchId,
                    "Placar da partida não informado"
            );
        }
    }

    private ScoringType calculateScoringType(
            Integer predictedHome,
            Integer predictedAway,
            Integer actualHome,
            Integer actualAway
    ) {
        if (predictedHome.equals(actualHome)
                && predictedAway.equals(actualAway)) {
            return ScoringType.EXACT_SCORE;
        }

        int predictedOutcome =
                Integer.compare(predictedHome, predictedAway);

        int actualOutcome =
                Integer.compare(actualHome, actualAway);

        if (predictedOutcome == actualOutcome) {
            return ScoringType.OUTCOME_ONLY;
        }

        return ScoringType.MISS;
    }

    private UUID currentUserId() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof JwtAuthenticationToken jwtAuthentication)) {
            throw new AccessDeniedException(
                    "Usuário autenticado não possui um token JWT válido"
            );
        }

        try {
            return UUID.fromString(jwtAuthentication.getToken().getSubject());
        } catch (IllegalArgumentException exception) {
            throw new AccessDeniedException(
                    "O claim 'sub' do token não contém um UUID válido"
            );
        }
    }
}