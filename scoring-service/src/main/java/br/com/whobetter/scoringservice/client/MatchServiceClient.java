package br.com.whobetter.scoringservice.client;

import br.com.whobetter.scoringservice.dto.MatchResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "match-service")
public interface MatchServiceClient {

    @GetMapping("/matches/{id}")
    MatchResponse findById(@PathVariable("id") UUID id);
}
