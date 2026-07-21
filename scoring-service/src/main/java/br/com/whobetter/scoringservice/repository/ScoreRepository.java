package br.com.whobetter.scoringservice.repository;

import br.com.whobetter.scoringservice.domain.Score;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ScoreRepository extends JpaRepository<Score, UUID> {
    boolean existsByPredictionId(UUID predictionId);
    boolean existsByMatchId(UUID matchId);
    List<Score> findByMatchId(UUID matchId);
    List<Score> findByGroupId(UUID groupId);
    List<Score> findByUserId(UUID userId);
}
