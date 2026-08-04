package br.com.whobetter.scoringservice.controller;

import br.com.whobetter.scoringservice.dto.ScoreMatchRequest;
import br.com.whobetter.scoringservice.dto.ScoreResponse;
import br.com.whobetter.scoringservice.mapper.ScoreMapper;
import br.com.whobetter.scoringservice.service.ScoreService;
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

@Tag(name = "Pontuações", description = "Operações de cálculo e consulta de pontuações")
@RestController
@RequestMapping("/scores")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class ScoreController {

    private final ScoreService scoreService;

    @Operation(
            summary = "Calcular pontuações da partida",
            description = "Calcula e persiste as pontuações associadas às previsões de uma partida"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Pontuações calculadas com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos"),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "404", description = "Partida ou previsões não encontradas"),
            @ApiResponse(responseCode = "409", description = "Conflito de negócio ao calcular pontuações")
    })
    @PostMapping("/calculate")
    public ResponseEntity<List<ScoreResponse>> scoreMatch(@Valid @RequestBody ScoreMatchRequest request) {
        List<ScoreResponse> responses = scoreService.scoreMatch(request.matchId())
                .stream()
                .map(ScoreMapper::toResponse)
                .toList();

        return ResponseEntity.status(HttpStatus.CREATED).body(responses);
    }

    @Operation(
            summary = "Buscar pontuação por ID",
            description = "Retorna uma pontuação específica a partir do UUID informado"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pontuação encontrada"),
            @ApiResponse(responseCode = "400", description = "UUID informado é inválido"),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "404", description = "Pontuação não encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ScoreResponse> findById(
            @Parameter(description = "UUID da pontuação", example = "550e8400-e29b-41d4-a716-446655440050")
            @PathVariable UUID id) {
        return ResponseEntity.ok(ScoreMapper.toResponse(scoreService.findById(id)));
    }

    @Operation(
            summary = "Listar pontuações por partida",
            description = "Retorna todas as pontuações associadas a uma partida específica"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pontuações retornadas com sucesso"),
            @ApiResponse(responseCode = "400", description = "UUID da partida inválido"),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "404", description = "Partida não encontrada")
    })
    @GetMapping("/match/{matchId}")
    public ResponseEntity<List<ScoreResponse>> findByMatchId(
            @Parameter(description = "UUID da partida", example = "550e8400-e29b-41d4-a716-446655440020")
            @PathVariable UUID matchId) {
        return ResponseEntity.ok(
                scoreService.findByMatchId(matchId).stream().map(ScoreMapper::toResponse).toList()
        );
    }

    @Operation(
            summary = "Listar pontuações por grupo",
            description = "Retorna todas as pontuações associadas a um grupo específico"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pontuações retornadas com sucesso"),
            @ApiResponse(responseCode = "400", description = "UUID do grupo inválido"),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "404", description = "Grupo não encontrado")
    })
    @GetMapping("/group/{groupId}")
    public ResponseEntity<List<ScoreResponse>> findByGroupId(
            @Parameter(description = "UUID do grupo", example = "550e8400-e29b-41d4-a716-446655440010")
            @PathVariable UUID groupId) {
        return ResponseEntity.ok(
                scoreService.findByGroupId(groupId).stream().map(ScoreMapper::toResponse).toList()
        );
    }

    @Operation(
            summary = "Listar pontuações por usuário",
            description = "Retorna todas as pontuações associadas a um usuário específico"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pontuações retornadas com sucesso"),
            @ApiResponse(responseCode = "400", description = "UUID do usuário inválido"),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ScoreResponse>> findByUserId(
            @Parameter(description = "UUID do usuário", example = "550e8400-e29b-41d4-a716-446655440000")
            @PathVariable UUID userId) {
        return ResponseEntity.ok(
                scoreService.findByUserId(userId).stream().map(ScoreMapper::toResponse).toList()
        );
    }
}