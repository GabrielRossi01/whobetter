package br.com.whobetter.rankingservice.controller;

import br.com.whobetter.rankingservice.dto.RankingEntryResponse;
import br.com.whobetter.rankingservice.dto.RefreshRankingRequest;
import br.com.whobetter.rankingservice.mapper.RankingMapper;
import br.com.whobetter.rankingservice.service.RankingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "Rankings", description = "Operações de atualização e consulta do ranking de grupos")
@RestController
@RequestMapping("/rankings")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class RankingController {

    private final RankingService rankingService;

    @Operation(
            summary = "Recalcular ranking do grupo",
            description = "Atualiza e retorna o ranking consolidado de um grupo com base nas pontuações disponíveis"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Ranking recalculado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos"),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "404", description = "Grupo não encontrado"),
            @ApiResponse(responseCode = "409", description = "Conflito de negócio ao recalcular ranking")
    })
    @PostMapping("/refresh")
    public ResponseEntity<List<RankingEntryResponse>> refresh(@Valid @RequestBody RefreshRankingRequest request) {
        List<RankingEntryResponse> responses = rankingService.refreshRanking(request.groupId()).stream()
                .map(RankingMapper::toResponse)
                .toList();

        return ResponseEntity.ok(responses);
    }

    @Operation(
            summary = "Listar ranking por grupo",
            description = "Retorna as posições do ranking associadas a um grupo específico"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Ranking retornado com sucesso"),
            @ApiResponse(responseCode = "400", description = "UUID do grupo inválido"),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "404", description = "Grupo não encontrado")
    })
    @GetMapping("/group/{groupId}")
    public ResponseEntity<List<RankingEntryResponse>> findByGroupId(
            @Parameter(description = "UUID do grupo", example = "550e8400-e29b-41d4-a716-446655440010")
            @PathVariable UUID groupId) {
        List<RankingEntryResponse> responses = rankingService.findByGroupId(groupId).stream()
                .map(RankingMapper::toResponse)
                .toList();

        return ResponseEntity.ok(responses);
    }
}