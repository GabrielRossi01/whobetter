package br.com.whobetter.rankingservice.controller;

import br.com.whobetter.rankingservice.dto.RankingEntryResponse;
import br.com.whobetter.rankingservice.dto.RefreshRankingRequest;
import br.com.whobetter.rankingservice.mapper.RankingMapper;
import br.com.whobetter.rankingservice.service.RankingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/rankings")
@RequiredArgsConstructor
public class RankingController {

    private final RankingService rankingService;

    @PostMapping("/refresh")
    public ResponseEntity<List<RankingEntryResponse>> refresh(@Valid @RequestBody RefreshRankingRequest request) {
        List<RankingEntryResponse> responses = rankingService.refreshRanking(request.groupId()).stream()
                .map(RankingMapper::toResponse)
                .toList();

        return ResponseEntity.ok(responses);
    }

    @GetMapping("/group/{groupId}")
    public ResponseEntity<List<RankingEntryResponse>> findByGroupId(@PathVariable UUID groupId) {
        List<RankingEntryResponse> responses = rankingService.findByGroupId(groupId).stream()
                .map(RankingMapper::toResponse)
                .toList();

        return ResponseEntity.ok(responses);
    }
}
