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
    public Group create(CreateGroupRequest request) {
        if (!userRepository.existsById(request.ownerId())) {
            throw new UserNotFoundException(request.ownerId());
        }

        String inviteCode = generateUniqueInviteCode();
        Group group = new Group(request.name(), inviteCode, request.ownerId());
        Group savedGroup = groupRepository.save(group);

        groupMemberRepository.save(new GroupMember(savedGroup.getId(), request.ownerId()));

        return savedGroup;
    }

    @Transactional
    public Group joinByInviteCode(String inviteCode, UUID userId) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException(userId);
        }

        String normalizedInviteCode = inviteCode.toUpperCase(Locale.ROOT);

        Group group = groupRepository.findByInviteCode(normalizedInviteCode)
                .orElseThrow(() -> new InviteCodeNotFoundException(inviteCode));

        boolean alreadyMember = groupMemberRepository
                .existsById_GroupIdAndId_UserId(group.getId(), userId);

        if (alreadyMember) {
            throw new UserAlreadyInGroupException(userId, group.getId());
        }

        groupMemberRepository.save(new GroupMember(group.getId(), userId));
        return group;
    }

    private String generateUniqueInviteCode() {
        String code;
        do {
            code = inviteCodeGenerator.generate();
        } while (groupRepository.existsByInviteCode(code));
        return code;
    }
}