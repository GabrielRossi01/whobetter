package br.com.whobetter.predictionservice.repository;

import br.com.whobetter.predictionservice.domain.Prediction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PredictionRepository extends JpaRepository<Prediction, UUID> {
    List<Prediction> findByMatchId(UUID matchId);
    List<Prediction> findByUserId(UUID userId);
    Optional<Prediction> findByMatchIdAndUserId(UUID matchId, UUID userId);
    boolean existsByMatchIdAndUserId(UUID matchId, UUID userId);
}
