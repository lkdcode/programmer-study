package com.sb.adapter.calligraphy.input.web.rest.command

import com.sb.framework.api.ApiResponseEntity
import com.sb.framework.api.success
import com.sb.framework.cloudflare.r2.R2PresignedUrlService
import com.sb.framework.util.PathUtil
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
class CalligraphyUploadApi(
    private val r2PresignedUrlService: R2PresignedUrlService,
) {

    @PostMapping("/public/calligraphies/{calligraphyId}/upload-url")
    suspend fun generateUploadUrl(
        @PathVariable calligraphyId: UUID,
    ): ApiResponseEntity<CalligraphyUploadUrlResponse> {
        val path = PathUtil.createCalligraphyPath(calligraphyId)
        val putUrl = r2PresignedUrlService.generatePutUrl(path, CONTENT_TYPE_PNG)

        return success(
            CalligraphyUploadUrlResponse(
                calligraphyId = calligraphyId.toString(),
                path = path,
                uploadUrl = putUrl,
            )
        )
    }

    companion object {
        private const val CONTENT_TYPE_PNG = "image/png"
    }
}

data class CalligraphyUploadUrlResponse(
    val calligraphyId: String,
    val path: String,
    val uploadUrl: String,
)