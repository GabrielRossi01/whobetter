package br.com.whobetter.userservice.controllers;

import br.com.whobetter.userservice.domain.Group;
import br.com.whobetter.userservice.dto.CreateGroupRequest;
import br.com.whobetter.userservice.dto.GroupResponse;
import br.com.whobetter.userservice.dto.JoinGroupRequest;
import br.com.whobetter.userservice.service.GroupService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/groups")
public class GroupController {

    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @PostMapping
    public ResponseEntity<GroupResponse> create(@Valid @RequestBody CreateGroupRequest request) {
        Group createdGroup = groupService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(GroupResponse.from(createdGroup));
    }

    @PostMapping("/join")
    public ResponseEntity<GroupResponse> join(@Valid @RequestBody JoinGroupRequest request) {
        Group group = groupService.joinByInviteCode(request.inviteCode(), request.userId());
        return ResponseEntity.ok(GroupResponse.from(group));
    }
}
