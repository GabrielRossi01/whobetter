package br.com.whobetter.rankingservice.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "ranking_entries")
@Getter
@NoArgsConstructor
public class RankingEntry {

    @Id
    private UUID id;

    @Column(name = "group_id", nullable = false)
    private UUID groupId;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(name = "total_points", nullable = false)
    private Integer totalPoints;

    @Column(name = "rankPosition", nullable = false)
    private Integer rankPosition;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public RankingEntry(UUID groupId, UUID userId, Integer totalPoints, Integer rankPosition) {
        this.id = UUID.randomUUID();
        this.groupId = groupId;
        this.userId = userId;
        this.totalPoints = totalPoints;
        this.rankPosition = rankPosition;
        this.updatedAt = LocalDateTime.now();
    }

    public void update(Integer totalPoints, Integer rankPosition) {
        this.totalPoints = totalPoints;
        this.rankPosition = rankPosition;
        this.updatedAt = LocalDateTime.now();
    }
}