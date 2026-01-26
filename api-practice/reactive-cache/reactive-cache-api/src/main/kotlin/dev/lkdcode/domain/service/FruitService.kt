package dev.lkdcode.domain.service

import dev.lkdcode.cache.annotation.ReactiveCacheEvict
import dev.lkdcode.cache.annotation.ReactiveCachePut
import dev.lkdcode.cache.annotation.ReactiveCacheable
import dev.lkdcode.domain.repository.FruitRepository
import dev.lkdcode.domain.value.Fruit
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class FruitService(
    private val fruitRepository: FruitRepository,
) {

    @ReactiveCacheable(
        key = "#fruitId",
        ttl = 30,
        condition = "#fruitId > 0",
        unless = "#result == null"
    )
    fun fetch(fruitId: Long): Mono<Fruit> {
        println("FruitService.fetch FRUIT_ID: $fruitId")

        return fruitRepository.findByFruitId(fruitId)
    }

    @ReactiveCachePut(
        key = "#fruitId",
        ttl = 30
    )
    fun create(fruitId: Long): Mono<Fruit> {
        println("FruitService.create FRUIT_ID: $fruitId")

        return fruitRepository.save(fruitId)
    }

    @ReactiveCachePut(
        key = "#fruitId",
        ttl = 30
    )
    fun update(fruitId: Long): Mono<Fruit> {
        println("ReactiveService.update FRUIT_ID: $fruitId")

        return fruitRepository.updateByFruitId(fruitId)
    }

    @ReactiveCacheEvict(
        key = "#fruitId",
    )
    fun delete(fruitId: Long): Mono<Fruit> {
        println("ReactiveService.delete FRUIT_ID: $fruitId")

        return fruitRepository.deleteByFruitId(fruitId)
    }
}