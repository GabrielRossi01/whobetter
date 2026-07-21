package br.com.whobetter.predictionservice.client;

import br.com.whobetter.predictionservice.dto.MatchResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@Validated
@FeignClient(name = "match-service")
public interface MatchServiceClient {

    @GetMapping("/matches{id}")
    MatchResponse findById(@PathVariable("id") UUID id);
}
