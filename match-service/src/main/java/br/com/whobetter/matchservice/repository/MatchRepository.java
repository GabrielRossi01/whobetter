package br.com.whobetter.matchservice.repository;

import br.com.whobetter.matchservice.domain.Match;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface MatchRepository extends JpaRepository<Match, UUID> {
    List<Match> findByGroupId(UUID groupId);
}
