package br.com.whobetter.predictionservice.service;

import br.com.whobetter.predictionservice.client.UserServiceClient;
import br.com.whobetter.predictionservice.dto.GroupMemberResponse;
import br.com.whobetter.predictionservice.exception.UserNotInGroupException;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.resilience.annotation.Retryable;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class MembershipValidator {

    private final UserServiceClient userServiceClient;

    @Retryable(
            includes = FeignException.class,
            excludes = FeignException.NotFound.class,
            maxRetries = 3,
            delay = 1000,
            multiplier = 2.0,
            maxDelay = 10000,
            jitter = 200
    )
    public void validateUserMembership(UUID groupId, UUID userId) {
        GroupMemberResponse response = userServiceClient.checkMembership(groupId, userId);

        if (!response.member()) {
            throw new UserNotInGroupException(userId, groupId);
        }
    }
}
