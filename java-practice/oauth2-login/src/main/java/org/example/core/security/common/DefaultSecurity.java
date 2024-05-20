package org.example.core.security.common;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.stereotype.Component;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Component
public class DefaultSecurity extends AbstractHttpConfigurer<DefaultSecurity, HttpSecurity> {
    private boolean flag;

    @Override
    public void init(HttpSecurity http) throws Exception {
        if (flag) {
            http
                    .csrf(AbstractHttpConfigurer::disable)
                    .cors(AbstractHttpConfigurer::disable)
                    .httpBasic(AbstractHttpConfigurer::disable)
                    .headers(AbstractHttpConfigurer::disable)
                    .sessionManagement(s -> s.sessionCreationPolicy(STATELESS))

//                    .exceptionHandling(exceptionHandlingCustomizer -> exceptionHandlingCustomizer
//                            .accessDeniedHandler(baseAccessDeniedHandler)
//                            .authenticationEntryPoint(baseAuthenticationEntryPoint))
            ;
        }
    }

    public void active() {
        this.flag = true;
    }
}
