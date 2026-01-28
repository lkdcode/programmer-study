package dev.lkdcode.domain.repository

import org.springframework.stereotype.Repository

@Repository
class MvcRepository {

    fun findByUserId(userId: Long): String {
        println("MvcRepository.findByUserId USER_ID: $userId")

        return "MvcRepository.findByUserId. USER_ID: $userId"
    }

    fun save(userId: Long): String {
        println("MvcRepository.save USER_ID: $userId")

        return "MvcRepository.save. USER_ID: $userId"
    }

    fun updateByUserId(userId: Long): String {
        println("MvcRepository.updateByUserId USER_ID: $userId")

        return "MvcRepository.updateByUserId. USER_ID: $userId"
    }

    fun deleteByUserId(userId: Long): String {
        println("MvcRepository.deleteByUserId USER_ID: $userId")

        return "MvcRepository.deleteByUserId. USER_ID: $userId"
    }
}