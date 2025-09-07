package demo.example.server.api

import demo.example.server.framework.crypt.DecryptId
import demo.example.server.framework.crypt.CryptoService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import kotlin.random.Random

@RestController
class CryptoPathApi(
    private val cryptoService: CryptoService,
) {

    @GetMapping("/encryptId")
    fun getEncryptId(): ResponseEntity<String> {
        val encryptId = Random
            .nextInt(1, 50)
            .toString()
            .let { cryptoService.encrypt(it) }

        return ResponseEntity.ok().body(encryptId)
    }

    @GetMapping("/{id}")
    fun encryptedId(
        @DecryptId @PathVariable(name = "id") id: Long,
    ) {
        println("ID : $id")
    }
}