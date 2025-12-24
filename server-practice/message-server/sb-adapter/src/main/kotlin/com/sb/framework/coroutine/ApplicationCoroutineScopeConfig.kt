package com.sb.framework.coroutine

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ApplicationCoroutineScopeConfig {

    @Bean
    fun applicationCoroutineScope(): CoroutineScope =
        CoroutineScope(
            SupervisorJob() + Dispatchers.IO
        )
}