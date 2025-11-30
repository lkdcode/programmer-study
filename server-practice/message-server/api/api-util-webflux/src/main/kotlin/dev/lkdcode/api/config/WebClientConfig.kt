package dev.lkdcode.api.config

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.http.MediaType
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.http.codec.json.Jackson2JsonDecoder
import org.springframework.http.codec.json.Jackson2JsonEncoder
import org.springframework.web.reactive.function.client.ExchangeStrategies
import org.springframework.web.reactive.function.client.WebClient
import reactor.netty.http.client.HttpClient

@Configuration
class WebClientConfig {

    @Primary
    @Bean
    fun webClient(builder: WebClient.Builder): WebClient {
        val httpClient = defaultHttpClient()

        val strategies = ExchangeStrategies.builder()
            .codecs { it.defaultCodecs().maxInMemorySize(300 * 1024 * 1024) }
            .build()

        return build(httpClient, builder, strategies)
    }

    @Qualifier("camelCaseWebClient")
    @Bean
    fun camelCaseWebClient(
        builder: WebClient.Builder,
        @Qualifier("camelCaseObjectMapper") camelCaseMapper: ObjectMapper
    ): WebClient {
        val httpClient = defaultHttpClient()

        val jacksonEncoder = Jackson2JsonEncoder(camelCaseMapper, MediaType.APPLICATION_JSON)
        val jacksonDecoder = Jackson2JsonDecoder(camelCaseMapper, MediaType.APPLICATION_JSON)

        val strategies = ExchangeStrategies.builder()
            .codecs {
                it.defaultCodecs().jackson2JsonDecoder(jacksonDecoder)
                it.defaultCodecs().jackson2JsonEncoder(jacksonEncoder)
                it.defaultCodecs().maxInMemorySize(16 * 1024 * 1024)
            }
            .build()

        return build(httpClient, builder, strategies)
    }

    private fun defaultHttpClient(): HttpClient = HttpClient
        .create()
        .httpResponseDecoder { it.maxHeaderSize(32 * 1024) }

    private fun build(
        httpClient: HttpClient,
        builder: WebClient.Builder,
        strategies: ExchangeStrategies
    ): WebClient = builder
        .clientConnector(ReactorClientHttpConnector(httpClient))
        .exchangeStrategies(strategies)
        .build()

}
