package dev.lkdcode

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.CachePut
import org.springframework.cache.annotation.Cacheable
import org.springframework.expression.spel.standard.SpelExpressionParser

@SpringBootApplication
class ReactiveCacheApplication

fun main(args: Array<String>) {
    runApplication<ReactiveCacheApplication>(*args)
}





@Cacheable
fun hi(){
    SpelExpressionParser
}

@CachePut
fun hi1(){}

@CacheEvict
fun hi2(){}

