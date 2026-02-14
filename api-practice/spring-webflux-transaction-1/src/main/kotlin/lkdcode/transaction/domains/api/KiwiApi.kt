package lkdcode.transaction.domains.api

import lkdcode.transaction.domains.model.Kiwi
import lkdcode.transaction.domains.service.KiwiNoBridgeService
import lkdcode.transaction.domains.service.KiwiNoSPIService
import lkdcode.transaction.domains.service.KiwiService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
class KiwiApi(
    private val kiwiService: KiwiService,
    private val kiwiNoBridgeService: KiwiNoBridgeService,
    private val kiwiNoSPIService: KiwiNoSPIService,
) {

    @DeleteMapping("/jooq/{name}")
    fun jooq(@PathVariable name: String): Response<List<Kiwi>> =
        kiwiService.deleteByNameThenFindAll(name)
            .map { ResponseEntity.ok(it) }

    @DeleteMapping("/no-bridge/{name}")
    fun noBridge(@PathVariable name: String): Response<List<Kiwi>> =
        kiwiNoBridgeService.deleteByNameThenFindAll(name)
            .map { ResponseEntity.ok(it) }

    @DeleteMapping("/no-spi/{name}")
    fun noSPI(@PathVariable name: String): Response<List<Kiwi>> =
        kiwiNoSPIService.deleteByNameThenFindAll(name)
            .map { ResponseEntity.ok(it) }

}

private typealias Response<T> = Mono<ResponseEntity<T>>
