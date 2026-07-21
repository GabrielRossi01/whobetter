package br.com.whobetter.matchservice.service;

import br.com.whobetter.matchservice.client.GroupServiceClient;
import br.com.whobetter.matchservice.domain.Match;
import br.com.whobetter.matchservice.dto.CreateMatchRequest;
import br.com.whobetter.matchservice.dto.SetMatchResultRequest;
import br.com.whobetter.matchservice.exception.GroupNotFoundException;

import br.com.whobetter.matchservice.exception.MatchNotFoundException;
import br.com.whobetter.matchservice.repository.MatchRepository;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MatchService {

    private final MatchRepository matchRepository;
    private final GroupServiceClient groupServiceClient;

    @Transactional
    public Match create(CreateMatchRequest request) {
        validateGroupExists(request.groupId());

        Match match = new Match(
                request.groupId(),
                request.title(),
                request.eventDate(),
                request.createdBy()
        );

        return matchRepository.save(match);
    }

    public Match findById(UUID id) {
        return matchRepository.findById(id)
                .orElseThrow(() -> new MatchNotFoundException(id));
    }

    public List<Match> findByGroupId(UUID groupId) {
        return matchRepository.findByGroupId(groupId);
    }

    @Transactional
    public Match close(UUID id) {
        Match match = findById(id);
        match.close();
        return matchRepository.save(match);
    }

    @Transactional
    public Match setResult(UUID id, SetMatchResultRequest request) {
        Match match = findById(id);
        match.setResult(request.homeScore(), request.awayScore());
        return matchRepository.save(match);
    }

    @Transactional
    public Match cancel(UUID id) {
        Match match = findById(id);
        match.cancel();
        return matchRepository.save(match);
    }

    private void validateGroupExists(UUID groupId) {
        try {
            groupServiceClient.findById(groupId);
        } catch (FeignException.NotFound ex) {
            throw new GroupNotFoundException(groupId);
        }
    }
}
