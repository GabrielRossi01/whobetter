package br.com.whobetter.matchservice.dto;

import java.util.UUID;

public record GroupResponse(
    UUID id,
    String name,
    UUID ownerId
) {}
