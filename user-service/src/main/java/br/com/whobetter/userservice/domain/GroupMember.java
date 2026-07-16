package br.com.whobetter.userservice.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "group_members")
@Getter
@Setter
@NoArgsConstructor
public class GroupMember {

    @EmbeddedId
    private GroupMemberId id;

    @Column(name = "joined_at", nullable = false, updatable = false)
    private Instant joinedAt;

    @Builder
    public GroupMember(UUID groupId, UUID userId) {
        this.id = new GroupMemberId(groupId, userId);
    }

    public GroupMember(GroupMemberId id) {
        this.id = id;
    }

    @PrePersist
    void onCreate() {
        this.joinedAt = Instant.now();
    }

}
