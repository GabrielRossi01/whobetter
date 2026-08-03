package br.com.whobetter.matchservice.service;

import br.com.whobetter.matchservice.client.GroupServiceClient;
import br.com.whobetter.matchservice.exception.GroupNotFoundException;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.resilience.annotation.Retryable;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class GroupValidator {

    private final GroupServiceClient  groupServiceClient;

    @Retryable(
            includes = FeignException.class,
            excludes = FeignException.NotFound.class,
            maxRetries = 3,
            delay = 1000,
            multiplier = 2.0,
            maxDelay = 10000,
            jitter = 200
    )
    public void validateGroupExists(UUID groupId) {
        try {
            groupServiceClient.findById(groupId);
        } catch (FeignException.NotFound ex) {
            throw new GroupNotFoundException(groupId);
        }
    }
}
