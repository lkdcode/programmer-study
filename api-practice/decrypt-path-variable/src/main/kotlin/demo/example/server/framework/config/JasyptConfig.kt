package demo.example.server.framework.config

import org.jasypt.encryption.StringEncryptor
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor
import org.jasypt.iv.NoIvGenerator
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class JasyptConfig {

    @Bean
    fun stringEncryptor(): StringEncryptor =
        StandardPBEStringEncryptor()
            .apply {
                this.setPassword("lkdcode")
                this.setIvGenerator(NoIvGenerator())
            }
}