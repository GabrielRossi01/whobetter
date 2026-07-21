package br.com.whobetter.predictionservice.dto;

import java.util.UUID;

public record GroupMemberResponse(
        UUID groupId,
        UUID userId,
        boolean member
) {}
