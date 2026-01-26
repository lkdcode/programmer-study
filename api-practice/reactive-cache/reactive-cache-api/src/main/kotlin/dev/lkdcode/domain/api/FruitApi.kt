package dev.lkdcode.domain.api

import dev.lkdcode.domain.service.FruitService
import dev.lkdcode.domain.value.Fruit
import org.springframework.web.bind.annotation.*

@RestController
class FruitApi(
    private val fruitService: FruitService,
) {

    @GetMapping("/fruits/{fruitId}")
    fun fetch(@PathVariable(name = "fruitId") fruitId: Long): MonoResponseEntity<Fruit> =
        fruitService
            .fetch(fruitId)
            .map { monoResponseEntity(it) }

    @PostMapping("/fruits/{fruitId}")
    fun create(@PathVariable(name = "fruitId") fruitId: Long): MonoResponseEntity<Fruit> =
        fruitService
            .create(fruitId)
            .map { monoResponseEntity(it) }

    @PutMapping("/fruits/{fruitId}")
    fun update(@PathVariable(name = "fruitId") fruitId: Long): MonoResponseEntity<Fruit> =
        fruitService
            .update(fruitId)
            .map { monoResponseEntity(it) }

    @DeleteMapping("/fruits/{fruitId}")
    fun delete(@PathVariable(name = "fruitId") fruitId: Long): MonoResponseEntity<Fruit> =
        fruitService
            .delete(fruitId)
            .map { monoResponseEntity(it) }
}