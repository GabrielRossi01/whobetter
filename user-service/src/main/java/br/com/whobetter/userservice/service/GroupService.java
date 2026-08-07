package br.com.whobetter.userservice.service;

import br.com.whobetter.userservice.domain.Group;
import br.com.whobetter.userservice.domain.GroupMember;
import br.com.whobetter.userservice.dto.CreateGroupRequest;
import br.com.whobetter.userservice.exception.InviteCodeNotFoundException;
import br.com.whobetter.userservice.exception.UserAlreadyInGroupException;
import br.com.whobetter.userservice.exception.UserNotFoundException;
import br.com.whobetter.userservice.repository.GroupMemberRepository;
import br.com.whobetter.userservice.repository.GroupRepository;
import br.com.whobetter.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GroupService {

    private final GroupRepository groupRepository;
    private final GroupMemberRepository groupMemberRepository;
    private final UserRepository userRepository;
    private final InviteCodeGenerator inviteCodeGenerator;

    @Transactional
    @PreAuthorize("hasAuthority('SCOPE_groups:write')")
    public Group create(CreateGroupRequest request) {
        UUID authenticatedUserId = currentUserId();

        if (!userRepository.existsById(authenticatedUserId)) {
            throw new UserNotFoundException(authenticatedUserId);
        }

        String inviteCode = generateUniqueInviteCode();

        Group group = new Group(
                request.name(),
                inviteCode,
                authenticatedUserId
        );

        Group savedGroup = groupRepository.save(group);

        groupMemberRepository.save(
                new GroupMember(
                        savedGroup.getId(),
                        authenticatedUserId
                )
        );

        return savedGroup;
    }

    @Transactional
    @PreAuthorize("hasAuthority('SCOPE_groups:write')")
    public Group joinByInviteCode(
            String inviteCode,
            UUID ignoredUserId
    ) {
        UUID authenticatedUserId = currentUserId();

        if (!userRepository.existsById(authenticatedUserId)) {
            throw new UserNotFoundException(authenticatedUserId);
        }

        String normalizedInviteCode =
                inviteCode.toUpperCase(Locale.ROOT);

        Group group = groupRepository
                .findByInviteCode(normalizedInviteCode)
                .orElseThrow(() ->
                        new InviteCodeNotFoundException(inviteCode)
                );

        boolean alreadyMember =
                groupMemberRepository
                        .existsById_GroupIdAndId_UserId(
                                group.getId(),
                                authenticatedUserId
                        );

        if (alreadyMember) {
            throw new UserAlreadyInGroupException(
                    authenticatedUserId,
                    group.getId()
            );
        }

        groupMemberRepository.save(
                new GroupMember(
                        group.getId(),
                        authenticatedUserId
                )
        );

        return group;
    }

    private String generateUniqueInviteCode() {
        String code;

        do {
            code = inviteCodeGenerator.generate();
        } while (groupRepository.existsByInviteCode(code));

        return code;
    }

    private UUID currentUserId() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof JwtAuthenticationToken jwtAuthentication)) {
            throw new AccessDeniedException(
                    "Usuário autenticado não possui um token JWT válido"
            );
        }

        try {
            return UUID.fromString(jwtAuthentication.getToken().getSubject());
        } catch (IllegalArgumentException exception) {
            throw new AccessDeniedException(
                    "O claim 'sub' do token não contém um UUID válido"
            );
        }
    }
}