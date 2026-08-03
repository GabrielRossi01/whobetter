package br.com.whobetter.predictionservice.service;

import br.com.whobetter.predictionservice.domain.Prediction;
import br.com.whobetter.predictionservice.dto.CreatePredictionRequest;
import br.com.whobetter.predictionservice.dto.UpdatePredictionRequest;
import br.com.whobetter.predictionservice.exception.DuplicatePredictionException;
import br.com.whobetter.predictionservice.exception.PredictionNotFoundException;
import br.com.whobetter.predictionservice.repository.PredictionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PredictionService {

    private final PredictionRepository predictionRepository;
    private final MatchValidator matchValidator;
    private final MembershipValidator membershipValidator;

    @Transactional
    public Prediction create(CreatePredictionRequest request) {
        matchValidator.validateMatchOpen(request.matchId(), request.groupId());
        membershipValidator.validateUserMembership(request.groupId(), request.userId());

        if (predictionRepository.existsByMatchIdAndUserId(request.matchId(), request.userId())) {
            throw new DuplicatePredictionException(request.matchId(), request.userId());
        }

        Prediction prediction = new Prediction(
                request.matchId(),
                request.groupId(),
                request.userId(),
                request.predictedHomeScore(),
                request.predictedAwayScore()
        );

        return predictionRepository.save(prediction);
    }

    public Prediction findById(UUID id) {
        return predictionRepository.findById(id)
                .orElseThrow(() -> new PredictionNotFoundException(id));
    }

    public List<Prediction> findByMatchId(UUID matchId) {
        return predictionRepository.findByMatchId(matchId);
    }

    public List<Prediction> findByUserId(UUID userId) {
        return predictionRepository.findByUserId(userId);
    }

    @Transactional
    public Prediction update(UUID id, UpdatePredictionRequest request) {
        Prediction prediction = findById(id);
        matchValidator.validateMatchOpen(prediction.getMatchId(), prediction.getGroupId());

        prediction.updateScores(request.predictedHomeScore(), request.predictedAwayScore());
        return predictionRepository.save(prediction);
    }

    @Transactional
    public void delete(UUID id) {
        Prediction prediction = findById(id);
        matchValidator.validateMatchOpen(prediction.getMatchId(), prediction.getGroupId());
        predictionRepository.delete(prediction);
    }
}