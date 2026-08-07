package br.com.whobetter.matchservice.service;

import br.com.whobetter.matchservice.domain.Match;
import br.com.whobetter.matchservice.dto.CreateMatchRequest;
import br.com.whobetter.matchservice.dto.SetMatchResultRequest;
import br.com.whobetter.matchservice.exception.MatchNotFoundException;
import br.com.whobetter.matchservice.messaging.MatchEventPublisher;
import br.com.whobetter.matchservice.messaging.MatchFinishedEvent;
import br.com.whobetter.matchservice.repository.MatchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MatchService {

    private final MatchRepository matchRepository;
    private final GroupValidator groupValidator;
    private final MatchEventPublisher matchEventPublisher;

    @Transactional
    @PreAuthorize("hasAuthority('SCOPE_matches:write')")
    public Match create(CreateMatchRequest request) {
        UUID authenticatedUserId = currentUserId();

        groupValidator.validateGroupExists(request.groupId());

        Match match = new Match(
                request.groupId(),
                request.title(),
                request.eventDate(),
                authenticatedUserId
        );

        return matchRepository.save(match);
    }

    @PreAuthorize("hasAuthority('SCOPE_matches:read')")
    public Match findById(UUID id) {
        return findMatch(id);
    }

    @PreAuthorize("hasAuthority('SCOPE_matches:read')")
    public List<Match> findByGroupId(UUID groupId) {
        return matchRepository.findByGroupId(groupId);
    }

    @Transactional
    @PreAuthorize("hasAuthority('SCOPE_matches:write')")
    public Match close(UUID id) {
        Match match = findMatch(id);

        match.close();

        return matchRepository.save(match);
    }

    @Transactional
    @PreAuthorize("hasAuthority('SCOPE_matches:write')")
    public Match setResult(
            UUID id,
            SetMatchResultRequest request
    ) {
        Match match = findMatch(id);

        match.setResult(
                request.homeScore(),
                request.awayScore()
        );

        Match savedMatch = matchRepository.save(match);

        MatchFinishedEvent event = new MatchFinishedEvent(
                savedMatch.getId(),
                savedMatch.getGroupId(),
                savedMatch.getHomeScore(),
                savedMatch.getAwayScore(),
                savedMatch.getStatus().name()
        );

        matchEventPublisher.publishMatchFinished(event);

        return savedMatch;
    }

    @Transactional
    @PreAuthorize("hasAuthority('SCOPE_matches:write')")
    public Match cancel(UUID id) {
        Match match = findMatch(id);

        match.cancel();

        return matchRepository.save(match);
    }

    private Match findMatch(UUID id) {
        return matchRepository.findById(id)
                .orElseThrow(() -> new MatchNotFoundException(id));
    }

    private UUID currentUserId() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof JwtAuthenticationToken jwtAuthentication)) {
            throw new IllegalStateException(
                    "Usuário autenticado não possui um token JWT válido"
            );
        }

        try {
            return UUID.fromString(jwtAuthentication.getToken().getSubject());
        } catch (IllegalArgumentException exception) {
            throw new IllegalStateException(
                    "O claim 'sub' do token não contém um UUID válido"
            );
        }
    }
}