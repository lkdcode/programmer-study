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
        // TODO: allowIfBaseType(Any::class.java)는 사실상 모든 클래스를 역직렬화 허용한다.
        //  Redis가 침해된 환경에서 @class 필드 조작을 통한 역직렬화 공격이 가능하다.
        //  저장 타입이 확정되면 아래처럼 패키지 단위로 제한해야 한다:
        //  .allowIfSubType("dev.lkdcode.")
        //  .allowIfSubType("java.util.")
        //  .allowIfSubType("java.lang.")
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