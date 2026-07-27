package br.com.whobetter.rankingservice.mapper;

import br.com.whobetter.rankingservice.domain.RankingEntry;
import br.com.whobetter.rankingservice.dto.RankingEntryResponse;

public final class RankingMapper {

    private RankingMapper() {}

    public static RankingEntryResponse toResponse(RankingEntry entry) {
        return new RankingEntryResponse(
               entry.getId(),
               entry.getGroupId(),
               entry.getUserId(),
               entry.getTotalPoints(),
               entry.getRankPosition(),
               entry.getUpdatedAt()
        );
    }
}
