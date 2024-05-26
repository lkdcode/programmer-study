package org.game.core.security.filter;

import lombok.RequiredArgsConstructor;
import org.game.core.security.common.DefaultSecurity;
import org.game.core.security.service.MyOAuth2UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class OAuth2Security {
    private final DefaultSecurity defaultSecurity;
    private final MyOAuth2UserService myOAuth2UserService;

    @Bean
    public SecurityFilterChain doFilterChain(HttpSecurity http) throws Exception {
        return http
                .securityMatcher("/**")
                .with(defaultSecurity, DefaultSecurity::active)
                .oauth2Login(oauth2 -> oauth2
                        .userInfoEndpoint(userInfoEndpointConfig -> userInfoEndpointConfig
                                .userService(myOAuth2UserService)))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/oauth2/**", "/login/**").permitAll()
                        .anyRequest().authenticated())
                .build();
    }
}