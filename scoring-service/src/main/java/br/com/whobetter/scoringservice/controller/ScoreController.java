package br.com.whobetter.scoringservice.controller;

import br.com.whobetter.scoringservice.dto.ScoreMatchRequest;
import br.com.whobetter.scoringservice.dto.ScoreResponse;
import br.com.whobetter.scoringservice.mapper.ScoreMapper;
import br.com.whobetter.scoringservice.service.ScoreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/scores")
@RequiredArgsConstructor
public class ScoreController {

    private final ScoreService scoreService;

    @PostMapping("/calculate")
    public ResponseEntity<List<ScoreResponse>> scoreMatch(@Valid @RequestBody ScoreMatchRequest request) {
        List<ScoreResponse> responses = scoreService.scoreMatch(request.matchId())
                .stream()
                .map(ScoreMapper::toResponse)
                .toList();

        return ResponseEntity.status(HttpStatus.CREATED).body(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScoreResponse> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(ScoreMapper.toResponse(scoreService.findById(id)));
    }

    @GetMapping("/match/{matchId}")
    public ResponseEntity<List<ScoreResponse>> findByMatchId(@PathVariable UUID matchId) {
        return ResponseEntity.ok(
                scoreService.findByMatchId(matchId).stream().map(ScoreMapper::toResponse).toList()
        );
    }

    @GetMapping("/group/{groupId}")
    public ResponseEntity<List<ScoreResponse>> findByGroupId(@PathVariable UUID groupId) {
        return ResponseEntity.ok(
                scoreService.findByGroupId(groupId).stream().map(ScoreMapper::toResponse).toList()
        );
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ScoreResponse>> findByUserId(@PathVariable UUID userId) {
        return ResponseEntity.ok(
                scoreService.findByUserId(userId).stream().map(ScoreMapper::toResponse).toList()
        );
    }
}