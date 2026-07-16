package br.com.whobetter.userservice.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "groups")
@Getter
@Setter
@NoArgsConstructor
public class Group {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false, length = 120)
    private String name;

    @Column(name = "invite_code", nullable = false, unique = true, length = 10)
    private String inviteCode;

    @Column(name = "owner_id", nullable = false)
    private UUID ownerId;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Builder
    public Group(String name, String inviteCode, UUID ownerId) {
        this.name = name;
        this.inviteCode = inviteCode;
        this.ownerId = ownerId;
    }

    @PrePersist
    void onCreate() {
        this.createdAt = Instant.now();
    }
}
