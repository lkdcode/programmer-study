package demo.example.server.framework.config

import demo.example.server.framework.crypt.DecryptIdConverter
import org.springframework.context.annotation.Configuration
import org.springframework.format.FormatterRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class CryptoConverterConfig(
    private val decryptIdConverter: DecryptIdConverter
) : WebMvcConfigurer {

    override fun addFormatters(registry: FormatterRegistry) =
        registry.addConverter(decryptIdConverter)
}