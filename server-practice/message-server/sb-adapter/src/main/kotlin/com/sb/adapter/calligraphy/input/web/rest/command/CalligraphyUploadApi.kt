package com.sb.adapter.calligraphy.input.web.rest.command

import com.sb.adapter.calligraphy.input.web.rest.command.request.CalligraphyUploadRequest
import com.sb.framework.api.ApiResponseEntity
import com.sb.framework.api.created
import com.sb.framework.cloudflare.r2.R2PresignedUrlService
import com.sb.framework.util.PathUtil
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class CalligraphyUploadApi(
    private val service: R2PresignedUrlService
) {

    @PostMapping("/public/calligraphies/{key}/upload-url")
    suspend fun getPut(
        @RequestBody request: CalligraphyUploadRequest.Upload
    ): ApiResponseEntity<String> {
        val path = PathUtil.createCalligraphyPath(request.key)

        return created(payload = service.generatePutUrl(path, request.contentType))
    }

    @PostMapping("/public/calligraphies/{key}/view-url")
    suspend fun getGet(
        @RequestBody request: CalligraphyUploadRequest.View
    ): ApiResponseEntity<String> {
        return created(payload = service.generateGetUrl(request.key))
    }
}