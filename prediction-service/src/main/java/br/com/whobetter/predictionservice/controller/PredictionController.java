package br.com.whobetter.predictionservice.controller;

import br.com.whobetter.predictionservice.domain.Prediction;
import br.com.whobetter.predictionservice.dto.CreatePredictionRequest;
import br.com.whobetter.predictionservice.dto.PredictionResponse;
import br.com.whobetter.predictionservice.dto.UpdatePredictionRequest;
import br.com.whobetter.predictionservice.mapper.PredictionMapper;
import br.com.whobetter.predictionservice.service.PredictionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "Previsões", description = "Operações de criação, consulta, atualização e remoção de previsões")
@RestController
@RequestMapping("/predictions")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class PredictionController {

    private final PredictionService predictionService;

    @Operation(
            summary = "Criar previsão",
            description = "Cria uma nova previsão associada a uma partida, grupo e usuário"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Previsão criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos"),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "404", description = "Partida, grupo ou usuário não encontrado"),
            @ApiResponse(responseCode = "409", description = "Conflito de negócio ao criar previsão")
    })
    @PostMapping
    public ResponseEntity<PredictionResponse> create(@Valid @RequestBody CreatePredictionRequest request) {
        Prediction prediction = predictionService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(PredictionMapper.toResponse(prediction));
    }

    @Operation(
            summary = "Buscar previsão por ID",
            description = "Retorna uma previsão específica a partir do UUID informado"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Previsão encontrada"),
            @ApiResponse(responseCode = "400", description = "UUID informado é inválido"),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "404", description = "Previsão não encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<PredictionResponse> findById(
            @Parameter(description = "Identificador UUID da previsão", example = "550e8400-e29b-41d4-a716-446655440030")
            @PathVariable UUID id) {
        return ResponseEntity.ok(PredictionMapper.toResponse(predictionService.findById(id)));
    }

    @Operation(
            summary = "Listar previsões por partida",
            description = "Retorna todas as previsões vinculadas a uma partida informada por query parameter"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Previsões retornadas com sucesso"),
            @ApiResponse(responseCode = "400", description = "Parâmetro matchId inválido"),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "404", description = "Partida não encontrada")
    })
    @GetMapping
    public ResponseEntity<List<PredictionResponse>> findByMatchId(
            @Parameter(description = "UUID da partida", example = "550e8400-e29b-41d4-a716-446655440020")
            @RequestParam UUID matchId) {
        List<PredictionResponse> responses = predictionService.findByMatchId(matchId)
                .stream()
                .map(PredictionMapper::toResponse)
                .toList();
        return ResponseEntity.ok(responses);
    }

    @Operation(
            summary = "Listar previsões por usuário",
            description = "Retorna todas as previsões realizadas por um usuário específico"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Previsões retornadas com sucesso"),
            @ApiResponse(responseCode = "400", description = "UUID do usuário inválido"),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PredictionResponse>> findByUserId(
            @Parameter(description = "UUID do usuário", example = "550e8400-e29b-41d4-a716-446655440000")
            @PathVariable UUID userId) {
        List<PredictionResponse> responses = predictionService.findByUserId(userId)
                .stream()
                .map(PredictionMapper::toResponse)
                .toList();
        return ResponseEntity.ok(responses);
    }

    @Operation(
            summary = "Atualizar previsão",
            description = "Atualiza os placares previstos de uma previsão existente"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Previsão atualizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos"),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "404", description = "Previsão não encontrada"),
            @ApiResponse(responseCode = "409", description = "Conflito de negócio ao atualizar previsão")
    })
    @PutMapping("/{id}")
    public ResponseEntity<PredictionResponse> update(
            @Parameter(description = "Identificador UUID da previsão", example = "550e8400-e29b-41d4-a716-446655440030")
            @PathVariable UUID id,
            @Valid @RequestBody UpdatePredictionRequest request
    ) {
        Prediction prediction = predictionService.update(id, request);
        return ResponseEntity.ok(PredictionMapper.toResponse(prediction));
    }

    @Operation(
            summary = "Excluir previsão",
            description = "Remove permanentemente uma previsão pelo identificador UUID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Previsão removida com sucesso"),
            @ApiResponse(responseCode = "400", description = "UUID informado é inválido"),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "404", description = "Previsão não encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "Identificador UUID da previsão", example = "550e8400-e29b-41d4-a716-446655440030")
            @PathVariable UUID id) {
        predictionService.delete(id);
        return ResponseEntity.noContent().build();
    }
}