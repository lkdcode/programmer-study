package org.multifilter.security.filter;


import lombok.RequiredArgsConstructor;
import org.multifilter.security.BaseSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BananaSecurityFilter {
    private static final String BANANA_API_PREFIX = "/banana";
    private static final String API = BANANA_API_PREFIX + "/**";
    private static final String[] ALLOW_LIST = {
            BANANA_API_PREFIX + "/get",
    };
    private static final String[] AUTHENTICATED = {
            BANANA_API_PREFIX + "/secret",
    };
    private final BaseSecurity baseSecurity;

    public SecurityFilterChain doFilterChain(HttpSecurity http) throws Exception {
        return http
                .securityMatcher(API)
                .with(baseSecurity, BaseSecurity::active)

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(ALLOW_LIST).permitAll()
                        .requestMatchers(AUTHENTICATED).authenticated()
                        .anyRequest().authenticated())

                .build();
    }
}