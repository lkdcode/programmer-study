package dev.lkdcode.api.client

import dev.lkdcode.api.exception.ApiException
import dev.lkdcode.api.response.ApiResponseCode
import dev.lkdcode.log.logInfo
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import java.util.function.Consumer

@Service
class ApiWebClientCamelCase(
    @Qualifier("camelCaseWebClient") private val webClient: WebClient,
) {

    fun <T> getRequest(url: String, headers: Consumer<HttpHeaders>, responseType: ParameterizedTypeReference<T>): T? {
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
            .block()
    }

    fun <T> getRequest(url: String, headers: Consumer<HttpHeaders>, responseType: Class<T>): T? {
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
            .block()
    }

    fun <T> postRequest(url: String, headers: Consumer<HttpHeaders>, responseType: Class<T>): T? {
        return webClient
            .post()
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
            .block()
    }
}