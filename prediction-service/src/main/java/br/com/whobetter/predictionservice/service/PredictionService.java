package br.com.whobetter.predictionservice.service;

import br.com.whobetter.predictionservice.domain.Prediction;
import br.com.whobetter.predictionservice.dto.CreatePredictionRequest;
import br.com.whobetter.predictionservice.dto.UpdatePredictionRequest;
import br.com.whobetter.predictionservice.exception.DuplicatePredictionException;
import br.com.whobetter.predictionservice.exception.PredictionNotFoundException;
import br.com.whobetter.predictionservice.repository.PredictionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
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
    @PreAuthorize("hasAuthority('SCOPE_predictions:write')")
    public Prediction create(CreatePredictionRequest request) {
        UUID authenticatedUserId = currentUserId();

        matchValidator.validateMatchOpen(
                request.matchId(),
                request.groupId()
        );

        membershipValidator.validateUserMembership(
                request.groupId(),
                authenticatedUserId
        );

        if (predictionRepository.existsByMatchIdAndUserId(
                request.matchId(),
                authenticatedUserId
        )) {
            throw new DuplicatePredictionException(
                    request.matchId(),
                    authenticatedUserId
            );
        }

        Prediction prediction = new Prediction(
                request.matchId(),
                request.groupId(),
                authenticatedUserId,
                request.predictedHomeScore(),
                request.predictedAwayScore()
        );

        return predictionRepository.save(prediction);
    }

    @PreAuthorize("hasAuthority('SCOPE_predictions:read')")
    public Prediction findById(UUID id) {
        return findPrediction(id);
    }

    @PreAuthorize("hasAuthority('SCOPE_predictions:read')")
    public List<Prediction> findByMatchId(UUID matchId) {
        return predictionRepository.findByMatchId(matchId);
    }

    @PreAuthorize("hasAuthority('SCOPE_predictions:read')")
    public List<Prediction> findByUserId(UUID userId) {
        UUID authenticatedUserId = currentUserId();

        if (!authenticatedUserId.equals(userId)) {
            throw new AccessDeniedException(
                    "O usuário não pode consultar previsões de outro usuário"
            );
        }

        return predictionRepository.findByUserId(userId);
    }

    @Transactional
    @PreAuthorize("hasAuthority('SCOPE_predictions:write')")
    public Prediction update(UUID id, UpdatePredictionRequest request) {
        UUID authenticatedUserId = currentUserId();
        Prediction prediction = findPrediction(id);

        ensureOwnership(prediction, authenticatedUserId);

        matchValidator.validateMatchOpen(
                prediction.getMatchId(),
                prediction.getGroupId()
        );

        prediction.updateScores(
                request.predictedHomeScore(),
                request.predictedAwayScore()
        );

        return predictionRepository.save(prediction);
    }

    @Transactional
    @PreAuthorize("hasAuthority('SCOPE_predictions:write')")
    public void delete(UUID id) {
        UUID authenticatedUserId = currentUserId();
        Prediction prediction = findPrediction(id);

        ensureOwnership(prediction, authenticatedUserId);

        matchValidator.validateMatchOpen(
                prediction.getMatchId(),
                prediction.getGroupId()
        );

        predictionRepository.delete(prediction);
    }

    private Prediction findPrediction(UUID id) {
        return predictionRepository.findById(id)
                .orElseThrow(() -> new PredictionNotFoundException(id));
    }

    private void ensureOwnership(
            Prediction prediction,
            UUID authenticatedUserId
    ) {
        if (!prediction.getUserId().equals(authenticatedUserId)) {
            throw new AccessDeniedException(
                    "O usuário não pode alterar esta previsão"
            );
        }
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