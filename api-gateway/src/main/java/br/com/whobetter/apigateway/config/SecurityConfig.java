package br.com.whobetter.apigateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableReactiveMethodSecurity
public class SecurityConfig {

    @Bean
    SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchange -> exchange
                        .pathMatchers(
                                "/v3/api-docs/**",
                                "/user-service/v3/api-docs",
                                "/match-service/v3/api-docs",
                                "/prediction-service/v3/api-docs",
                                "/ranking-service/v3/api-docs",
                                "/scoring-service/v3/api-docs",
                                "/swagger-ui.html",
                                "/swagger-ui/**",
                                "/webjars/**",
                                "/actuator/health"
                        ).permitAll()

                        .pathMatchers(HttpMethod.POST, "/api/users")
                        .permitAll()
                        .pathMatchers(HttpMethod.GET, "/api/users/**")
                        .hasAuthority("SCOPE_users:read")

                        .pathMatchers(HttpMethod.POST, "/api/groups/**")
                        .hasAuthority("SCOPE_groups:write")

                        .pathMatchers(HttpMethod.GET, "/api/matches/**")
                        .hasAuthority("SCOPE_matches:read")
                        .pathMatchers(HttpMethod.POST, "/api/matches/**")
                        .hasAuthority("SCOPE_matches:write")
                        .pathMatchers(HttpMethod.PATCH, "/api/matches/**")
                        .hasAuthority("SCOPE_matches:write")

                        .pathMatchers(HttpMethod.GET, "/api/predictions/**")
                        .hasAuthority("SCOPE_predictions:read")
                        .pathMatchers(HttpMethod.POST, "/api/predictions/**")
                        .hasAuthority("SCOPE_predictions:write")
                        .pathMatchers(HttpMethod.PUT, "/api/predictions/**")
                        .hasAuthority("SCOPE_predictions:write")
                        .pathMatchers(HttpMethod.DELETE, "/api/predictions/**")
                        .hasAuthority("SCOPE_predictions:write")

                        .pathMatchers(HttpMethod.GET, "/api/rankings/**")
                        .hasAuthority("SCOPE_rankings:read")
                        .pathMatchers(HttpMethod.POST, "/api/rankings/refresh")
                        .hasAuthority("SCOPE_rankings:refresh")

                        .pathMatchers(HttpMethod.GET, "/api/scores/**")
                        .hasAuthority("SCOPE_scores:read")
                        .pathMatchers(HttpMethod.POST, "/api/scores/calculate")
                        .hasAuthority("SCOPE_scores:calculate")

                        .anyExchange()
                        .authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(Customizer.withDefaults()))
                .build();
    }
}