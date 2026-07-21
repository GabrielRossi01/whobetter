package br.com.whobetter.matchservice.controller;

import br.com.whobetter.matchservice.domain.Match;
import br.com.whobetter.matchservice.dto.CreateMatchRequest;
import br.com.whobetter.matchservice.dto.MatchResponse;
import br.com.whobetter.matchservice.dto.SetMatchResultRequest;
import br.com.whobetter.matchservice.mapper.MatchMapper;
import br.com.whobetter.matchservice.service.MatchService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/matches")
@RequiredArgsConstructor
public class MatchController {

    private final MatchService matchService;

    @PostMapping
    public ResponseEntity<MatchResponse> create(@Valid @RequestBody CreateMatchRequest request){
        Match match = matchService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(MatchMapper.toResponse(match));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MatchResponse> findById(@PathVariable UUID id){
        Match match = matchService.findById(id);
        return ResponseEntity.ok(MatchMapper.toResponse(match));
    }

    @GetMapping
    public ResponseEntity<List<MatchResponse>> findByGroupId(@RequestParam UUID groupId){
        List<MatchResponse> matches = matchService.findByGroupId(groupId).stream()
                .map(MatchMapper::toResponse)
                .toList();
        return ResponseEntity.ok(matches);
    }

    @PatchMapping("/{id}/close")
    public ResponseEntity<MatchResponse> close(@PathVariable UUID id){
        Match match = matchService.close(id);
        return ResponseEntity.ok(MatchMapper.toResponse(match));
    }

    @PatchMapping("/{id}/result")
    public ResponseEntity<MatchResponse> setResult(@PathVariable UUID id, @Valid @RequestBody SetMatchResultRequest request){
        Match match = matchService.setResult(id, request);
        return ResponseEntity.ok(MatchMapper.toResponse(match));
    }

    @PatchMapping("/{id}/cancel")
    public ResponseEntity<MatchResponse> cancel(@PathVariable UUID id){
        Match match = matchService.cancel(id);
        return ResponseEntity.ok(MatchMapper.toResponse(match));
    }
}
