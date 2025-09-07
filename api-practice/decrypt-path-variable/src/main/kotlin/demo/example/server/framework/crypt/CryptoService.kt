package demo.example.server.framework.crypt

import org.jasypt.encryption.StringEncryptor
import org.springframework.stereotype.Service
import java.util.*

@Service
class CryptoService(
    private val stringEncryptor: StringEncryptor
) {

    fun encrypt(target: String): String =
        stringEncryptor.encrypt(target)
            .toByteArray(Charsets.UTF_8)
            .let { Base64.getUrlEncoder().withoutPadding().encodeToString(it) }

    fun decryptId(target: String): Long =
            Base64.getUrlDecoder()
                .decode(target)
                .decodeToString()
                .let { stringEncryptor.decrypt(it).toLong() }
}