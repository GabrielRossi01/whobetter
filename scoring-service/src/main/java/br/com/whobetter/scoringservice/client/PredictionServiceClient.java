package br.com.whobetter.scoringservice.client;

import br.com.whobetter.scoringservice.dto.PredictionResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "prediction-service")
public interface PredictionServiceClient {

    @GetMapping("/predictions")
    List<PredictionResponse> findByMatchId(@RequestParam("matchId") UUID matchId);
}
