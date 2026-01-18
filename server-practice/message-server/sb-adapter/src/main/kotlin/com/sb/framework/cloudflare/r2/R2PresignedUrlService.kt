package com.sb.framework.cloudflare.r2

import com.sb.framework.cloudflare.config.CloudflareR2Properties
import org.springframework.stereotype.Service
import software.amazon.awssdk.services.s3.model.GetObjectRequest
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import software.amazon.awssdk.services.s3.presigner.S3Presigner
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest

@Service
class R2PresignedUrlService(
    private val presigner: S3Presigner,
    private val r2Properties: CloudflareR2Properties
) {

    fun generateGetUrl(key: String): String {
        val getObjectRequest = GetObjectRequest
            .builder()
            .bucket(r2Properties.bucketName)
            .key(key)
            .build()

        val presignRequest = GetObjectPresignRequest
            .builder()
            .signatureDuration(r2Properties.getExpiredTTL)
            .getObjectRequest(getObjectRequest)
            .build()

        return presigner.presignGetObject(presignRequest).url().toString()
    }

    fun generatePutUrl(
        key: String,
        contentType: String,
    ): String {
        val putObjectRequest = PutObjectRequest
            .builder()
            .bucket(r2Properties.bucketName)
            .key(key)
            .contentType(contentType)
            .build()

        val presignRequest = PutObjectPresignRequest
            .builder()
            .signatureDuration(r2Properties.putExpiredTTL)
            .putObjectRequest(putObjectRequest)
            .build()

        return presigner.presignPutObject(presignRequest).url().toString()
    }
}