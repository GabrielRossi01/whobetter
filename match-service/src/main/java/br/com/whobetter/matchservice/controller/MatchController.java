package br.com.whobetter.matchservice.controller;

import br.com.whobetter.matchservice.domain.Match;
import br.com.whobetter.matchservice.dto.CreateMatchRequest;
import br.com.whobetter.matchservice.dto.MatchResponse;
import br.com.whobetter.matchservice.dto.SetMatchResultRequest;
import br.com.whobetter.matchservice.mapper.MatchMapper;
import br.com.whobetter.matchservice.service.MatchService;
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

@Tag(name = "Partidas", description = "Operações de atualização do ciclo de vida das partidas")
@RestController
@RequestMapping("/matches")
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
public class MatchController {

    private final MatchService matchService;

    @Operation(
            summary = "Criar partida",
            description = "Cria uma nova partida associada a um grupo e agenda sua data de ocorrência"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Partida criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos"),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "404", description = "Grupo ou usuário criador não encontrado"),
            @ApiResponse(responseCode = "409", description = "Conflito de negócio ao criar partida")
    })
    @PostMapping
    public ResponseEntity<MatchResponse> create(@Valid @RequestBody CreateMatchRequest request){
        Match match = matchService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(MatchMapper.toResponse(match));
    }

    @Operation(
            summary = "Buscar partida por ID",
            description = "Retorna os dados completos de uma partida a partir do seu identificador UUID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Partida encontrada"),
            @ApiResponse(responseCode = "400", description = "UUID informado é inválido"),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "404", description = "Partida não encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<MatchResponse> findById(
            @Parameter(description = "Identificador UUID da partida", example = "550e8400-e29b-41d4-a716-446655440020")
            @PathVariable UUID id){
        Match match = matchService.findById(id);
        return ResponseEntity.ok(MatchMapper.toResponse(match));
    }

    @Operation(
            summary = "Listar partidas por grupo",
            description = "Retorna todas as partidas vinculadas a um grupo informado por query parameter"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Partidas retornadas com sucesso"),
            @ApiResponse(responseCode = "400", description = "Parâmetro groupId inválido"),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "404", description = "Grupo não encontrado")
    })
    @GetMapping
    public ResponseEntity<List<MatchResponse>> findByGroupId(
            @Parameter(description = "UUID do grupo ao qual as partidas pertencem", example = "550e8400-e29b-41d4-a716-446655440010")
            @RequestParam UUID groupId){
        List<MatchResponse> matches = matchService.findByGroupId(groupId).stream()
                .map(MatchMapper::toResponse)
                .toList();
        return ResponseEntity.ok(matches);
    }

    @Operation(
            summary = "Encerrar partida",
            description = "Altera o status da partida para encerrada"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Partida encerrada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Operação inválida para o estado atual da partida"),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "404", description = "Partida não encontrada"),
            @ApiResponse(responseCode = "409", description = "Conflito de estado ao encerrar partida")
    })
    @PatchMapping("/{id}/close")
    public ResponseEntity<MatchResponse> close(
            @Parameter(description = "UUID da partida a ser encerrada", example = "550e8400-e29b-41d4-a716-446655440020")
            @PathVariable UUID id){
        Match match = matchService.close(id);
        return ResponseEntity.ok(MatchMapper.toResponse(match));
    }

    @Operation(
            summary = "Definir resultado da partida",
            description = "Define o placar final da partida com gols do mandante e do visitante"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Resultado da partida definido com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos ou operação não permitida"),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "404", description = "Partida não encontrada"),
            @ApiResponse(responseCode = "409", description = "Conflito de estado ao definir resultado")
    })
    @PatchMapping("/{id}/result")
    public ResponseEntity<MatchResponse> setResult(
            @Parameter(description = "UUID da partida cujo resultado será definido", example = "550e8400-e29b-41d4-a716-446655440020")
            @PathVariable UUID id, @Valid @RequestBody SetMatchResultRequest request){
        Match match = matchService.setResult(id, request);
        return ResponseEntity.ok(MatchMapper.toResponse(match));
    }

    @Operation(
            summary = "Cancelar partida",
            description = "Altera o status da partida para cancelada"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Partida cancelada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Operação inválida para o estado atual da partida"),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "404", description = "Partida não encontrada"),
            @ApiResponse(responseCode = "409", description = "Conflito de estado ao cancelar partida")
    })
    @PatchMapping("/{id}/cancel")
    public ResponseEntity<MatchResponse> cancel(
            @Parameter(description = "UUID da partida a ser cancelada", example = "550e8400-e29b-41d4-a716-446655440020")
            @PathVariable UUID id){
        Match match = matchService.cancel(id);
        return ResponseEntity.ok(MatchMapper.toResponse(match));
    }
}
