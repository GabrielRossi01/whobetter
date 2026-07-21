package br.com.whobetter.scoringservice.mapper;

import br.com.whobetter.scoringservice.domain.Score;
import br.com.whobetter.scoringservice.dto.ScoreResponse;

public final class ScoreMapper {

    private ScoreMapper() {}

    public static ScoreResponse toResponse(Score score) {
        return new ScoreResponse(
                score.getId(),
                score.getMatchId(),
                score.getGroupId(),
                score.getUserId(),
                score.getPredictionId(),
                score.getPoints(),
                score.getScoringType().name(),
                score.getCreatedAt()
        );
    }
}
