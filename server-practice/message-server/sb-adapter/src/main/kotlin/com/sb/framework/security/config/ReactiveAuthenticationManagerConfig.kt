package com.sb.framework.security.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.security.authentication.ReactiveAuthenticationManager
import reactor.core.publisher.Mono

@Configuration
class ReactiveAuthenticationManagerConfig {

    @Bean
    @Primary
    fun defaultAuthManager(): ReactiveAuthenticationManager =
        ReactiveAuthenticationManager { Mono.empty() }
}