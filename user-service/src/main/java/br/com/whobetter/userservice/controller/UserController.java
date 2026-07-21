package br.com.whobetter.userservice.controller;

import br.com.whobetter.userservice.domain.User;
import br.com.whobetter.userservice.dto.CreateUserRequest;
import br.com.whobetter.userservice.dto.UserResponse;
import br.com.whobetter.userservice.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserResponse> create(@Valid @RequestBody CreateUserRequest request) {
        User createdUser = userService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(UserResponse.from(createdUser));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> findById(@PathVariable UUID id) {
        User user = userService.findById(id);
        return ResponseEntity.ok(UserResponse.from(user));
    }
}
