package br.com.whobetter.userservice.repository;

import br.com.whobetter.userservice.domain.GroupMember;
import br.com.whobetter.userservice.domain.GroupMemberId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface GroupMemberRepository extends JpaRepository<GroupMember, GroupMemberId> {

    boolean existsById_GroupIdAndId_UserId(UUID groupId, UUID userId);
}
