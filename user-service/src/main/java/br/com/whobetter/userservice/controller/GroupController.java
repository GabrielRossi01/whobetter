package br.com.whobetter.userservice.controller;

import br.com.whobetter.userservice.domain.Group;
import br.com.whobetter.userservice.dto.CreateGroupRequest;
import br.com.whobetter.userservice.dto.GroupResponse;
import br.com.whobetter.userservice.dto.JoinGroupRequest;
import br.com.whobetter.userservice.service.GroupService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Grupos", description = "Operações de criação e participação em grupos")
@RestController
@RequestMapping("/groups")
@SecurityRequirement(name = "bearerAuth")
public class GroupController {

    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @Operation(
            summary = "Criar grupo",
            description = "Cria um novo grupo e define o usuário owner como responsável inicial pelo grupo"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Grupo criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos"),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "404", description = "Usuário owner não encontrado"),
            @ApiResponse(responseCode = "409", description = "Conflito de negócio ao criar grupo")
    })
    @PostMapping
    public ResponseEntity<GroupResponse> create(@Valid @RequestBody CreateGroupRequest request) {
        Group createdGroup = groupService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(GroupResponse.from(createdGroup));
    }

    @Operation(
            summary = "Entrar em grupo com convite",
            description = "Adiciona um usuário a um grupo existente a partir de um código de convite válido"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuário adicionado ao grupo com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos"),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "404", description = "Grupo ou usuário não encontrado"),
            @ApiResponse(responseCode = "409", description = "Usuário já pertence ao grupo ou código inválido")
    })
    @PostMapping("/join")
    public ResponseEntity<GroupResponse> join(@Valid @RequestBody JoinGroupRequest request) {
        Group group = groupService.joinByInviteCode(request.inviteCode(), request.userId());
        return ResponseEntity.ok(GroupResponse.from(group));
    }
}