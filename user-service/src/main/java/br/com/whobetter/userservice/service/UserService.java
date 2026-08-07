package br.com.whobetter.userservice.service;

import br.com.whobetter.userservice.domain.User;
import br.com.whobetter.userservice.dto.CreateUserRequest;
import br.com.whobetter.userservice.exception.EmailAlreadyUsedException;
import br.com.whobetter.userservice.exception.UserNotFoundException;
import br.com.whobetter.userservice.repository.UserRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordHasher passwordHasher;

    public UserService(UserRepository userRepository, PasswordHasher passwordHasher) {
        this.userRepository = userRepository;
        this.passwordHasher = passwordHasher;
    }

    public User create(CreateUserRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new EmailAlreadyUsedException(request.email());
        }

        String hashedPassword = passwordHasher.hash(request.password());
        User user = new User(request.name(), request.email(), hashedPassword);
        return userRepository.save(user);
    }

    @PreAuthorize("hasAuthority('SCOPE_users:read')")
    public User findById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }
}
