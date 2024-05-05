package org.multifilter.security;


import lombok.RequiredArgsConstructor;
import org.multifilter.security.handler.BaseAccessDeniedHandler;
import org.multifilter.security.handler.BaseAuthenticationEntryPoint;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.stereotype.Component;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Component
@RequiredArgsConstructor
public class BaseSecurity extends AbstractHttpConfigurer<BaseSecurity, HttpSecurity> {
    private final BaseAccessDeniedHandler customAccessDeniedHandler;
    private final BaseAuthenticationEntryPoint customAuthenticationEntryPoint;

    private boolean flag;

    @Override
    public void init(HttpSecurity http) throws Exception {
        if (flag) {
            http
                    .csrf(AbstractHttpConfigurer::disable)
                    .cors(AbstractHttpConfigurer::disable)
                    .httpBasic(AbstractHttpConfigurer::disable)

                    .headers(h -> h.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                    .sessionManagement(s -> s.sessionCreationPolicy(STATELESS))

                    .exceptionHandling(exceptionHandlingCustomizer -> exceptionHandlingCustomizer
                            .accessDeniedHandler(customAccessDeniedHandler)
                            .authenticationEntryPoint(customAuthenticationEntryPoint))
            ;
        }
    }

    public void active() {
        this.flag = true;
    }
}

