package br.com.whobetter.predictionservice.client;

import br.com.whobetter.predictionservice.dto.GroupMemberResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@Validated
@FeignClient(name = "user-service")
public interface UserServiceClient {

    @GetMapping("/groups/members/check")
    GroupMemberResponse checkMembership(
            @RequestParam("groupId")UUID groupId,
            @RequestParam("userId") UUID userId
    );
}
