package br.com.whobetter.rankingservice.service;

import br.com.whobetter.rankingservice.client.ScoringServiceClient;
import br.com.whobetter.rankingservice.domain.RankingEntry;
import br.com.whobetter.rankingservice.dto.ScoreResponse;
import br.com.whobetter.rankingservice.exception.RankingNotFoundException;
import br.com.whobetter.rankingservice.repository.RankingEntryRepository;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.resilience.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RankingService {

    private final RankingEntryRepository rankingEntryRepository;
    private final ScoringServiceClient scoringServiceClient;

    @Retryable(
            includes = FeignException.class,
            excludes = FeignException.NotFound.class,
            maxRetries = 3,
            delay = 1000,
            multiplier = 2.0,
            maxDelay = 10000,
            jitter = 200
    )
    @Transactional
    public List<RankingEntry> refreshRanking(UUID groupId) {
        List<ScoreResponse> scores = scoringServiceClient.findByGroupId(groupId);

        Map<UUID, Integer> totalsByUser = scores.stream()
                .collect(Collectors.groupingBy(
                        ScoreResponse::userId,
                        Collectors.summingInt(ScoreResponse::points)
                ));

        List<Map.Entry<UUID, Integer>> sorted = totalsByUser.entrySet().stream()
                .sorted(Map.Entry.<UUID, Integer>comparingByValue().reversed())
                .toList();

        List<RankingEntry> updatedEntries = new ArrayList<>();
        int rankPosition = 1;

        for (Map.Entry<UUID, Integer> entry : sorted) {
            UUID userId = entry.getKey();
            Integer totalPoints = entry.getValue();
            int currentPosition = rankPosition;

            RankingEntry rankingEntry = rankingEntryRepository.findByGroupIdAndUserId(groupId, userId)
                    .orElseGet(() -> new RankingEntry(groupId, userId, totalPoints, currentPosition));

            rankingEntry.update(totalPoints, currentPosition);
            updatedEntries.add(rankingEntry);
            rankPosition++;
        }

        return rankingEntryRepository.saveAll(updatedEntries);
    }

    public List<RankingEntry> findByGroupId(UUID groupId) {
        List<RankingEntry> entries = rankingEntryRepository.findByGroupIdOrderByRankPositionAsc(groupId);

        if (entries.isEmpty()) {
            throw new RankingNotFoundException(groupId);
        }

        return entries;
    }
}