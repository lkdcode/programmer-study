package org.multifilter.security.filter;


import lombok.RequiredArgsConstructor;
import org.multifilter.security.BaseSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FinalSecurityFilter {
    private static final String API = "/**";
    private final BaseSecurity baseSecurity;

    public SecurityFilterChain doFilterChain(HttpSecurity http) throws Exception {
        return http
                .securityMatcher(API)
                .with(baseSecurity, BaseSecurity::active)

                .authorizeHttpRequests(auth -> auth.anyRequest().authenticated())

                .build();
    }
}