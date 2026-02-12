package lkdcode.transaction.domains.api

import lkdcode.transaction.domains.model.Kiwi
import lkdcode.transaction.domains.service.KiwiNoBridgeService
import lkdcode.transaction.domains.service.KiwiNoContextNoSubscriberService
import lkdcode.transaction.domains.service.KiwiNoSubscriberService
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
    private val kiwiNoContextNoSubscriberService: KiwiNoContextNoSubscriberService,
    private val kiwiNoSubscriberService: KiwiNoSubscriberService,
) {

    @DeleteMapping("/bridge/{name}")
    fun bridge(@PathVariable name: String): Response<List<Kiwi>> =
        kiwiService.deleteByNameThenFindAll(name)
            .map { ResponseEntity.ok(it) }

    @DeleteMapping("/no-bridge/{name}")
    fun noBridge(@PathVariable name: String): Response<List<Kiwi>> =
        kiwiNoBridgeService.deleteByNameThenFindAll(name)
            .map { ResponseEntity.ok(it) }

    @DeleteMapping("/no-context-no-subscriber/{name}")
    fun noContextNoSubscriber(@PathVariable name: String): Response<List<Kiwi>> =
        kiwiNoContextNoSubscriberService.deleteByNameThenFindAll(name)
            .map { ResponseEntity.ok(it) }

    @DeleteMapping("/no-subscriber/{name}")
    fun noSubscriber(@PathVariable name: String): Response<List<Kiwi>> =
        kiwiNoSubscriberService.deleteByNameThenFindAll(name)
            .map { ResponseEntity.ok(it) }
}

private typealias Response<T> = Mono<ResponseEntity<T>>