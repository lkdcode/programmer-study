package com.sb.framework.security.config

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.codec.json.Jackson2JsonDecoder
import org.springframework.http.codec.json.Jackson2JsonEncoder
import org.springframework.web.reactive.function.server.HandlerStrategies

@Configuration
class HandlerStrategiesConfig {

    @Bean
    fun defaultHandlerStrategies(objectMapper: ObjectMapper): HandlerStrategies {
        return HandlerStrategies.builder().codecs {
            it.defaultCodecs().jackson2JsonDecoder(Jackson2JsonDecoder(objectMapper))
            it.defaultCodecs().jackson2JsonEncoder(Jackson2JsonEncoder(objectMapper))
        }.build()
    }
}