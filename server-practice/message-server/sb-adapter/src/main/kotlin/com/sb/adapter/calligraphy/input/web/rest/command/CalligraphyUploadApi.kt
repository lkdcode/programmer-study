package com.sb.adapter.calligraphy.input.web.rest.command

import com.sb.framework.api.ApiResponseEntity
import com.sb.framework.api.created
import com.sb.framework.cloudflare.r2.R2PresignedUrlService
import com.sb.framework.util.PathUtil
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RestController

@RestController
class CalligraphyUploadApi(
    private val service: R2PresignedUrlService
) {

    @PostMapping("/public/calligraphies/{key}/upload-url")
    suspend fun getPut(
        @PathVariable(name = "key") key: String,
        @RequestHeader("X-Upload-Content-Type") contentType: String
    ): ApiResponseEntity<String> {
        val path = PathUtil.getCalligraphyPath

        return created(payload = service.generatePutUrl(path, contentType))
    }

    @PostMapping("/public/calligraphies/{key}/view-url")
    suspend fun getGet(
        @PathVariable(name = "key") key: String
    ): ApiResponseEntity<String> {
        return created(payload = service.generateGetUrl(key))
    }
}