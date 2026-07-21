package br.com.whobetter.predictionservice.controller;

import br.com.whobetter.predictionservice.domain.Prediction;
import br.com.whobetter.predictionservice.dto.CreatePredictionRequest;
import br.com.whobetter.predictionservice.dto.PredictionResponse;
import br.com.whobetter.predictionservice.dto.UpdatePredictionRequest;
import br.com.whobetter.predictionservice.mapper.PredictionMapper;
import br.com.whobetter.predictionservice.service.PredictionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/predictions")
@RequiredArgsConstructor
public class PredictionController {

    private final PredictionService predictionService;

    @PostMapping
    public ResponseEntity<PredictionResponse> create(@Valid @RequestBody CreatePredictionRequest request) {
        Prediction prediction = predictionService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(PredictionMapper.toResponse(prediction));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PredictionResponse> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(PredictionMapper.toResponse(predictionService.findById(id)));
    }

    @GetMapping
    public ResponseEntity<List<PredictionResponse>> findByMatchId(@RequestParam UUID matchId) {
        List<PredictionResponse> responses = predictionService.findByMatchId(matchId)
                .stream()
                .map(PredictionMapper::toResponse)
                .toList();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PredictionResponse>> findByUserId(@PathVariable UUID userId) {
        List<PredictionResponse> responses = predictionService.findByUserId(userId)
                .stream()
                .map(PredictionMapper::toResponse)
                .toList();
        return ResponseEntity.ok(responses);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PredictionResponse> update(
            @PathVariable UUID id,
            @Valid @RequestBody UpdatePredictionRequest request
    ) {
        Prediction prediction = predictionService.update(id, request);
        return ResponseEntity.ok(PredictionMapper.toResponse(prediction));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        predictionService.delete(id);
        return ResponseEntity.noContent().build();
    }
}