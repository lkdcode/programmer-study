package org.multifilter.security.config;

import lombok.RequiredArgsConstructor;
import org.multifilter.security.filter.FinalSecurityFilter;
import org.multifilter.security.filter.AppleSecurityFilter;
import org.multifilter.security.filter.BananaSecurityFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class FilterConfig {
    private final AppleSecurityFilter appleSecurityFilter;
    private final BananaSecurityFilter bananaSecurityFilter;

    private final FinalSecurityFilter filter;

    @Bean
    @Order(1)
    public SecurityFilterChain appleSecurityFilterChain(HttpSecurity http) throws Exception {
        return appleSecurityFilter.doFilterChain(http);
    }

    @Bean
    @Order(2)
    public SecurityFilterChain bananaSecurityFilterChain(HttpSecurity http) throws Exception {
        return bananaSecurityFilter.doFilterChain(http);
    }

    @Bean
    @Order(3)
    public SecurityFilterChain loginFilterChain(HttpSecurity http) throws Exception {
        return filter.doFilterChain(http);
    }
}