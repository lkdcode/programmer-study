package com.sb.adapter.calligraphy.input.web.rest.command

import com.sb.framework.api.ApiResponseEntity
import com.sb.framework.api.created
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class CalligraphyWebhookApi {

    @PostMapping("/api/calligraphies/webhook")
    suspend fun getWebhook(
        @RequestBody request: CalligraphyWebhookRequest,
    ): ApiResponseEntity<Unit> {
        println(request)

        return created<Unit>()
    }
}

data class CalligraphyWebhookRequest(
    val calligraphyId: String,
    val status: WebhookStatus,
    val progress: Double
)