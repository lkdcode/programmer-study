package com.sb.adapter.calligraphy.input.web.sse

import com.sb.framework.api.FluxApiEntity
import com.sb.framework.security.authentication.UserAuthentication
import org.springframework.http.MediaType
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController


@RestController
class CalligraphyEventStream(
) {

    @GetMapping(
        value = ["/sse/calligraphies/event"],
        produces = [MediaType.TEXT_EVENT_STREAM_VALUE]
    )
    suspend fun subscribe(
        @AuthenticationPrincipal auth: UserAuthentication,
    ): FluxApiEntity<String> {
        TODO()
    }
}