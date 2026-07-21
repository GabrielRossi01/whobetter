package br.com.whobetter.matchservice.domain;

import br.com.whobetter.matchservice.exception.InvalidMatchStatusException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "matches")
@Getter
@NoArgsConstructor
public class Match {

    @Id
    private UUID id;

    @Column(name = "group_id", nullable = false)
    private UUID groupId;

    @Column(nullable = false, length = 150)
    private String title;

    @Column(name = "event_date", nullable = false)
    private LocalDateTime eventDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private MatchStatus status;

    @Column(name = "home_score")
    private Integer homeScore;

    @Column(name = "away_score")
    private Integer awayScore;

    @Column(name = "created_by", nullable = false)
    private UUID createdBy;

    @Column(name = "created_at", nullable = false)
    private  LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private  LocalDateTime updatedAt;

    public Match(UUID groupId, String title, LocalDateTime eventDate, UUID createdBy) {
        this.id = UUID.randomUUID();
        this.groupId = groupId;
        this.title = title;
        this.eventDate = eventDate;
        this.status = MatchStatus.OPEN;
        this.createdBy = createdBy;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public void close() {
        if (status != MatchStatus.OPEN) {
            throw new InvalidMatchStatusException(id, status, MatchStatus.CLOSED);
        }
        this.status = MatchStatus.CLOSED;
        this.updatedAt = LocalDateTime.now();
    }

    public void setResult(int homeScore, int awayScore) {
        if (status != MatchStatus.CLOSED) {
            throw new InvalidMatchStatusException(id, status, MatchStatus.FINISHED);
        }
        this.homeScore = homeScore;
        this.awayScore = awayScore;
        this.status = MatchStatus.FINISHED;
        this.updatedAt = LocalDateTime.now();
    }

    public void cancel() {
        if (status == MatchStatus.FINISHED || status == MatchStatus.CANCELLED) {
            throw new InvalidMatchStatusException(id, status, MatchStatus.CANCELLED);
        }
        this.status = MatchStatus.CANCELLED;
        this.updatedAt = LocalDateTime.now();
    }
}
