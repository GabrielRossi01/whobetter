package br.com.whobetter.matchservice.mapper;

import br.com.whobetter.matchservice.domain.Match;
import br.com.whobetter.matchservice.dto.MatchResponse;

public final class MatchMapper {

    private MatchMapper() {}

    public static MatchResponse toResponse(Match match) {
        return new MatchResponse(
                match.getId(),
                match.getGroupId(),
                match.getTitle(),
                match.getEventDate(),
                match.getStatus(),
                match.getHomeScore(),
                match.getAwayScore(),
                match.getCreatedBy()
        );
    }
}
