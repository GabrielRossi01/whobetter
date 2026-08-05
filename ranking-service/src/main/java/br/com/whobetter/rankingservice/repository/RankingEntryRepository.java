package br.com.whobetter.rankingservice.repository;

import br.com.whobetter.rankingservice.domain.RankingEntry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RankingEntryRepository extends JpaRepository<RankingEntry, UUID> {
    List<RankingEntry> findByGroupIdOrderByRankPositionAsc(UUID groupId);
    Optional<RankingEntry> findByGroupIdAndUserId(UUID groupId, UUID userId);
}
