package dev.lkdcode.kafka.framework.config

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.json.JsonWriteFeature
import com.fasterxml.jackson.databind.*
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer
import com.fasterxml.jackson.datatype.jsr310.deser.key.LocalDateTimeKeyDeserializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import java.math.BigDecimal
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Configuration
class ObjectMapperConfig {

    private fun getDefaultObjectMapper(): ObjectMapper =
        jacksonObjectMapper()
            .registerModules(
                JavaTimeModule().apply {
                    addSerializer(BigDecimal::class.java, BigDecimalSerializer())

                    addSerializer(LocalDateTime::class.java, LocalDateTimeSerializer(TIME_FORMAT))
                    addDeserializer(LocalDateTime::class.java, LocalDateTimeDeserializer(TIME_FORMAT))

                    addKeySerializer(LocalDateTime::class.java, LdtKeySerializer())
                    addKeyDeserializer(LocalDateTime::class.java, LocalDateTimeKeyDeserializer.INSTANCE)
                }
            )
            .configure(JsonWriteFeature.WRITE_NUMBERS_AS_STRINGS.mappedFeature(), true)
            .configure(JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN, true)
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)

    @Bean
    @Primary
    fun objectMapper(): ObjectMapper = getDefaultObjectMapper()
        .setPropertyNamingStrategy(PropertyNamingStrategies.LOWER_CAMEL_CASE)

    @Bean
    @Qualifier("snakeCaseObjectMapper")
    fun camelCaseObjectMapper(): ObjectMapper = getDefaultObjectMapper()
        .setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE)
}

val TIME_FORMAT: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

class LdtKeySerializer : JsonSerializer<LocalDateTime>() {
    override fun serialize(value: LocalDateTime, gen: JsonGenerator, serializers: SerializerProvider) {
        gen.writeFieldName(value.format(TIME_FORMAT))
    }
}

class BigDecimalSerializer : JsonSerializer<BigDecimal>() {
    override fun serialize(value: BigDecimal, gen: JsonGenerator, serializers: SerializerProvider?) {
        var v = value.stripTrailingZeros()
        if (v.scale() < 0) v = v.setScale(0)
        gen.writeNumber(v)
    }
}