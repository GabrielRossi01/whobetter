package br.com.whobetter.predictionservice.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "predictions")
@Getter
@NoArgsConstructor
public class Prediction {

    @Id
    private UUID id;

    @Column(name = "match_id", nullable = false)
    private UUID matchId;

    @Column(name = "group_id",nullable = false)
    private UUID groupId;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(name = "predicted_home_score", nullable = false)
    private Integer predictedHomeScore;

    @Column(name = "predicted_away_score", nullable = false)
    private Integer predictedAwayScore;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public Prediction(UUID matchId, UUID groupId, UUID userId, Integer predictedHomeScore, Integer predictedAwayScore) {
        this.id = UUID.randomUUID();
        this.matchId = matchId;
        this.groupId = groupId;
        this.userId = userId;
        this.predictedHomeScore = predictedHomeScore;
        this.predictedAwayScore = predictedAwayScore;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public void updateScores(Integer predictedHomeScore, Integer predictedAwayScore) {
        this.predictedHomeScore = predictedHomeScore;
        this.predictedAwayScore = predictedAwayScore;
        this.updatedAt = LocalDateTime.now();
    }
}
