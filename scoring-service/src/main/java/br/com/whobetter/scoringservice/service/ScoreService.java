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
import lombok.RequiredArgsConstructor;
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

    @Transactional
    public List<Score> scoreMatch(UUID matchId) {
        MatchResponse match = matchServiceClient.findById(matchId);

        if (!"FINISHED".equalsIgnoreCase(match.status())) {
            throw new MatchNotFinishedException(matchId, match.status());
        }

        if (scoreRepository.existsByMatchId(matchId)) {
            throw new ScoreAlreadyCalculatedException(matchId);
        }

        List<PredictionResponse> predictions = predictionServiceClient.findByMatchId(matchId);
        List<Score> scores = new ArrayList<>();

        for (PredictionResponse prediction : predictions) {
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

            scores.add(new Score(
                    prediction.matchId(),
                    prediction.groupId(),
                    prediction.userId(),
                    prediction.id(),
                    points,
                    scoringType
            ));
        }

        List<Score> savedScores = scoreRepository.saveAll(scores);

        List<UUID> affectedUserIds = savedScores.stream()
                .map(Score::getUserId)
                .distinct()
                .toList();

        scoreEventPublisher.publishScoresCalculated(
                new ScoresCalculatedEvent(matchId, match.groupId(), affectedUserIds)
        );

        return savedScores;
    }

    public Score findById(UUID id) {
        return scoreRepository.findById(id)
                .orElseThrow(() -> new ScoreNotFoundException(id));
    }

    public List<Score> findByMatchId(UUID matchId) {
        return scoreRepository.findByMatchId(matchId);
    }

    public List<Score> findByGroupId(UUID groupId) {
        return scoreRepository.findByGroupId(groupId);
    }

    public List<Score> findByUserId(UUID userId) {
        return scoreRepository.findByUserId(userId);
    }

    private ScoringType calculateScoringType(
            Integer predictedHome,
            Integer predictedAway,
            Integer actualHome,
            Integer actualAway
    ) {
        if (predictedHome.equals(actualHome) && predictedAway.equals(actualAway)) {
            return ScoringType.EXACT_SCORE;
        }

        int predictedOutcome = Integer.compare(predictedHome, predictedAway);
        int actualOutcome = Integer.compare(actualHome, actualAway);

        if (predictedOutcome == actualOutcome) {
            return ScoringType.OUTCOME_ONLY;
        }

        return ScoringType.MISS;
    }
}