package dev.lkdcode.domain.service

import dev.lkdcode.domain.repository.MvcRepository
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.CachePut
import org.springframework.cache.annotation.Cacheable
import org.springframework.cache.interceptor.CacheAspectSupport
import org.springframework.cache.interceptor.CacheInterceptor
import org.springframework.stereotype.Service

@Service
class MvcService(
    private val mvcRepository: MvcRepository,
) {

    @Cacheable(
        value = ["MvcCacheManager"],
        keyGenerator = "mvcKeyGenerator",
        condition = "#userId % 2 == 0",
        unless = "#userId == 4"
    )
    fun fetch(userId: Long): String {
        println("MvcService.fetch USER_ID: $userId")

        return "→ MvcService.fetch → " + mvcRepository.findByUserId(userId)
    }

    @CachePut(
        keyGenerator = "mvcKeyGenerator",
        condition = "#userId % 3 == 0"
    )
    fun create(userId: Long): String {
        println("MvcService.create USER_ID: $userId")

        return "→ MvcService.create → " + mvcRepository.save(userId)
    }

    @CachePut(
        keyGenerator = "mvcKeyGenerator",
        condition = "#userId % 3 == 0"
    )
    fun update(userId: Long): String {
        println("MvcService.update USER_ID: $userId")

        return "→ MvcService.update → " + mvcRepository.updateByUserId(userId)
    }

    @CacheEvict(
        keyGenerator = "mvcKeyGenerator",
        condition = "#userId % 2 == 0"
    )
    fun delete(userId: Long): String {
        println("MvcService.delete USER_ID: $userId")

        return "→ MvcService.delete → " + mvcRepository.deleteByUserId(userId)
    }
}