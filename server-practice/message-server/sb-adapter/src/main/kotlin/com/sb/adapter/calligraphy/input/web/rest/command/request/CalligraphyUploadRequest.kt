package com.sb.adapter.calligraphy.input.web.rest.command.request

import java.util.*

interface CalligraphyUploadRequest {
    data class Upload(
        val key: UUID,
        val contentType: String
    )

    data class View(
        val key: String
    )
}