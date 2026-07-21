package br.com.whobetter.scoringservice.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "scores")
@Getter
@NoArgsConstructor
public class Score {

    @Id
    private UUID id;

    @Column(name = "match_id", nullable = false)
    private UUID matchId;

    @Column(name = "group_id", nullable = false)
    private UUID groupId;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(name = "prediction_id", nullable = false, unique = true)
    private UUID predictionId;

    @Column(nullable = false)
    private Integer points;

    @Enumerated(EnumType.STRING)
    @Column(name = "scoring_type", nullable = false)
    private ScoringType scoringType;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public Score(UUID matchId, UUID groupId, UUID userId, UUID predictionId, Integer points, ScoringType scoringType) {
        this.id = UUID.randomUUID();
        this.matchId = matchId;
        this.groupId = groupId;
        this.userId = userId;
        this.predictionId = predictionId;
        this.points = points;
        this.scoringType = scoringType;
        this.createdAt = LocalDateTime.now();
    }
}
