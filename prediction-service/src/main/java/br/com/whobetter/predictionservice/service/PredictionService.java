package br.com.whobetter.predictionservice.service;

import br.com.whobetter.predictionservice.client.MatchServiceClient;
import br.com.whobetter.predictionservice.client.UserServiceClient;
import br.com.whobetter.predictionservice.domain.Prediction;
import br.com.whobetter.predictionservice.dto.CreatePredictionRequest;
import br.com.whobetter.predictionservice.dto.GroupMemberResponse;
import br.com.whobetter.predictionservice.dto.MatchResponse;
import br.com.whobetter.predictionservice.dto.UpdatePredictionRequest;
import br.com.whobetter.predictionservice.exception.DuplicatePredictionException;
import br.com.whobetter.predictionservice.exception.MatchClosedException;
import br.com.whobetter.predictionservice.exception.PredictionNotFoundException;
import br.com.whobetter.predictionservice.exception.UserNotInGroupException;
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
    private final MatchServiceClient matchServiceClient;
    private final UserServiceClient userServiceClient;

    @Transactional
    public Prediction create(CreatePredictionRequest request) {
        validateMatchOpen(request.matchId(), request.groupId());
        validateUserMembership(request.groupId(), request.userId());

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
        validateMatchOpen(prediction.getMatchId(), prediction.getGroupId());

        prediction.updateScores(request.predictedHomeScore(), request.predictedAwayScore());
        return predictionRepository.save(prediction);
    }

    @Transactional
    public void delete(UUID id) {
        Prediction prediction = findById(id);
        validateMatchOpen(prediction.getMatchId(), prediction.getGroupId());
        predictionRepository.delete(prediction);
    }

    private void validateMatchOpen(UUID matchId, UUID expectedGroupId) {
        MatchResponse match = matchServiceClient.findById(matchId);

        if (!match.groupId().equals(expectedGroupId)) {
            throw new MatchClosedException(matchId, "GROUP_MISMATCH");
        }

        if (!"OPEN".equalsIgnoreCase(match.status())) {
            throw new MatchClosedException(matchId, match.status());
        }
    }

    private void validateUserMembership(UUID groupId, UUID userId) {
        GroupMemberResponse response = userServiceClient.checkMembership(groupId, userId);

        if (!response.member()) {
            throw new UserNotInGroupException(userId, groupId);
        }
    }
}
