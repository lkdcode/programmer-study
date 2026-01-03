package com.sb.adapter.like.input.web.rest.query.request

interface GetLikedCalligraphiesRequest {
    data class CursorQuery(
        val targetId: String? = null,
    )
}