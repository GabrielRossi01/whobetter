package br.com.whobetter.rankingservice.client;

import br.com.whobetter.rankingservice.dto.ScoreResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "scoring-service")
public interface ScoringServiceClient {

    @GetMapping("/scores/group/{groupId}")
    List<ScoreResponse> findByGroupId(@PathVariable("groupId")UUID groupId);
}
