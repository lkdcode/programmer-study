package dev.lkdcode.cache.config

import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import dev.lkdcode.cache.resolver.KotlinTypeResolver
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ObjectMapperConfig {

    @Bean
    fun redisTemplateObjectMapper(): ObjectMapper {
        val typeValidator = BasicPolymorphicTypeValidator
            .builder()
            .allowIfBaseType(Any::class.java)
            .build()

        val typeResolver = KotlinTypeResolver(ObjectMapper.DefaultTyping.NON_FINAL, typeValidator)
            .init(JsonTypeInfo.Id.CLASS, null)
            .inclusion(JsonTypeInfo.As.PROPERTY)

        return jacksonObjectMapper()
            .registerModule(JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            .setDefaultTyping(typeResolver)
    }
}