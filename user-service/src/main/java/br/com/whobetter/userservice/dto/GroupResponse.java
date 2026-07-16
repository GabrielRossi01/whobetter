package br.com.whobetter.userservice.dto;

import br.com.whobetter.userservice.domain.Group;

import java.time.Instant;
import java.util.UUID;

public record GroupResponse(
        UUID id,
        String name,
        String inviteCode,
        UUID ownerId,
        Instant createdAt
) {
    public static GroupResponse from(Group group) {
        return new GroupResponse(
                group.getId(),
                group.getName(),
                group.getInviteCode(),
                group.getOwnerId(),
                group.getCreatedAt()
        );
    }
}
