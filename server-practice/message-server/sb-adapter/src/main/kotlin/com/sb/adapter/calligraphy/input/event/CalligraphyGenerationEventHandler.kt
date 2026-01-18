package com.sb.adapter.calligraphy.input.event

import com.sb.adapter.calligraphy.input.event.dto.ImageGenerationRequest
import com.sb.adapter.calligraphy.input.event.dto.ImageGenerationResponse
import com.sb.application.calligraphy.event.CalligraphyGenerationEvent
import com.sb.framework.aiserver.AiServerProperties
import com.sb.framework.api.ApiWebClient
import com.sb.framework.log.logError
import com.sb.framework.log.logInfo
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class CalligraphyGenerationEventHandler(
    private val apiWebClient: ApiWebClient,
    private val aiServerProperties: AiServerProperties,
) {

    @EventListener
    fun handle(event: CalligraphyGenerationEvent) {
        val calligraphy = event.aggregate.snapshot

        val request = ImageGenerationRequest(
            fileName = "${calligraphy.id.value}.png",
            text = calligraphy.text.value,
            prompt = calligraphy.prompt?.value,
            uuid = calligraphy.id.value
        )

        apiWebClient
            .postRequest(
                url = "${aiServerProperties.baseUrl}/api/images/generate",
                body = request,
                responseType = ImageGenerationResponse::class.java
            ).subscribe(
                { response -> logInfo("FastAPI 호출 성공: calligraphyId=${response.uuid}, status=${response.status}") },
                { error -> logError("FastAPI 호출 실패: calligraphyId=${calligraphy.id.value}", error) }
            )
    }
}