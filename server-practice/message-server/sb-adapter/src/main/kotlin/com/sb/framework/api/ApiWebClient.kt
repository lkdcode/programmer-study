package com.sb.framework.api

import com.sb.framework.log.logInfo
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import java.util.function.Consumer

@Component
class ApiWebClient(
    private val webClient: WebClient,
) {

    fun <T> getRequest(
        url: String,
        headers: Consumer<HttpHeaders>,
        responseType: ParameterizedTypeReference<T>
    ): Mono<T> {
        return webClient
            .get()
            .uri(url)
            .headers(headers)
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .onStatus({ it.is4xxClientError }) {
                it.bodyToMono(String::class.java).map { body ->
                    logInfo("${ApiResponseCode.WEB_CLIENT_IS_4XX_ERROR.message}: $body")

                    throw ApiException(ApiResponseCode.WEB_CLIENT_IS_4XX_ERROR)
                }
            }
            .onStatus({ it.is5xxServerError }) {
                it.bodyToMono(String::class.java).map { body ->
                    logInfo("${ApiResponseCode.WEB_CLIENT_IS_5XX_ERROR.message}: $body")

                    throw ApiException(ApiResponseCode.WEB_CLIENT_IS_5XX_ERROR)
                }
            }
            .bodyToMono(responseType)
    }

    fun <T> getRequest(
        url: String,
        headers: Consumer<HttpHeaders>,
        responseType: Class<T>
    ): Mono<T> {
        return webClient
            .get()
            .uri(url)
            .headers(headers)
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .onStatus({ it.is4xxClientError }) {
                it.bodyToMono(String::class.java).map { body ->
                    logInfo("${ApiResponseCode.WEB_CLIENT_IS_4XX_ERROR.message}\n$body")

                    throw ApiException(ApiResponseCode.WEB_CLIENT_IS_4XX_ERROR)
                }
            }
            .onStatus({ it.is5xxServerError }) {
                it.bodyToMono(String::class.java).map { body ->
                    logInfo("${ApiResponseCode.WEB_CLIENT_IS_5XX_ERROR.message}\n$body")

                    throw ApiException(ApiResponseCode.WEB_CLIENT_IS_5XX_ERROR)
                }
            }
            .bodyToMono(responseType)
    }

    fun <T, BODY> postRequest(
        url: String,
        headers: Consumer<HttpHeaders>? = null,
        body: BODY? = null,
        responseType: Class<T>
    ): Mono<T> {
        return webClient
            .post()
            .uri(url)
            .apply {
                body?.let {
                    bodyValue(it)
                }
            }
            .apply {
                headers?.let {
                    headers(it)
                }
            }
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .onStatus({ it.is4xxClientError }) {
                it.bodyToMono(String::class.java).map { body ->
                    logInfo("${ApiResponseCode.WEB_CLIENT_IS_4XX_ERROR.message}\n$body")

                    throw ApiException(ApiResponseCode.WEB_CLIENT_IS_4XX_ERROR)
                }
            }
            .onStatus({ it.is5xxServerError }) {
                it.bodyToMono(String::class.java).map { body ->
                    logInfo("${ApiResponseCode.WEB_CLIENT_IS_5XX_ERROR.message}\n$body")

                    throw ApiException(ApiResponseCode.WEB_CLIENT_IS_5XX_ERROR)
                }
            }
            .bodyToMono(responseType)
    }
}