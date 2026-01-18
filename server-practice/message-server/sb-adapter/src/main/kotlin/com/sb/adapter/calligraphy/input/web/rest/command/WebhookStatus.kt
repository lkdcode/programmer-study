package com.sb.adapter.calligraphy.input.web.rest.command

enum class WebhookStatus {
    PENDING(),
    PROCESSING(),

    COMPLETED(),
    FAILED(),

    CANCELLED(),
    REJECTED(),
}