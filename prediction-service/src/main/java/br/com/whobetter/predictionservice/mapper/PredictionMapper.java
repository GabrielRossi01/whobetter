package br.com.whobetter.predictionservice.mapper;

import br.com.whobetter.predictionservice.domain.Prediction;
import br.com.whobetter.predictionservice.dto.PredictionResponse;

public final class PredictionMapper {

    private PredictionMapper() {}

    public static PredictionResponse toResponse(Prediction prediction) {
        return new PredictionResponse(
                prediction.getId(),
                prediction.getMatchId(),
                prediction.getGroupId(),
                prediction.getUserId(),
                prediction.getPredictedHomeScore(),
                prediction.getPredictedAwayScore(),
                prediction.getCreatedAt(),
                prediction.getUpdatedAt()
        );
    }
}
