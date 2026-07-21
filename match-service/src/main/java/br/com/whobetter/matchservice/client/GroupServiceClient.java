package br.com.whobetter.matchservice.client;

import br.com.whobetter.matchservice.dto.GroupResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "user-service")
public interface GroupServiceClient {

    @GetMapping("/groups/{id}")
    GroupResponse findById(@PathVariable("id") UUID id);
}
