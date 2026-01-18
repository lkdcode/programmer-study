package com.sb.framework.cloudflare.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.S3Configuration
import software.amazon.awssdk.services.s3.presigner.S3Presigner
import java.net.URI

@Configuration
class CloudflareR2ClientConfig {

    @Bean
    fun r2Client(property: CloudflareR2Properties): S3Client {
        val credentials = AwsBasicCredentials.create(property.accessKey, property.secretKey)

        return S3Client.builder()
            .endpointOverride(URI.create(property.endpoint))
            .credentialsProvider(StaticCredentialsProvider.create(credentials))
            .region(property.region)
            .serviceConfiguration(
                S3Configuration.builder()
                    .pathStyleAccessEnabled(true)
                    .build()
            )
            .build()
    }

    @Bean
    fun r2Presigner(r2Properties: CloudflareR2Properties): S3Presigner {
        val credentials = AwsBasicCredentials.create(r2Properties.accessKey, r2Properties.secretKey)

        return S3Presigner.builder()
            .endpointOverride(URI.create(r2Properties.endpoint))
            .credentialsProvider(StaticCredentialsProvider.create(credentials))
            .region(r2Properties.region)
            .serviceConfiguration(
                S3Configuration.builder()
                    .pathStyleAccessEnabled(true)
                    .build()
            )
            .build()
    }
}