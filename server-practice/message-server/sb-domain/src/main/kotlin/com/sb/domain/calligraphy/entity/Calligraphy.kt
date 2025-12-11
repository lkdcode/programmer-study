package com.sb.domain.calligraphy.entity

import com.sb.domain.calligraphy.value.*
import java.time.Instant

data class Calligraphy(
    val id: CalligraphyId,
    val seed: Seed,
    val prompt: Prompt,
    val style: StyleType,
    val user: User,
    val result: CalligraphyResult,
    val createdAt: Instant,
    val updatedAt: Instant,
) {
    @JvmInline
    value class CalligraphyId(val value: Long)

    fun createNext(
        additionalPrompt: Prompt,
        newStyle: StyleType,
        newResult: CalligraphyResult,
        newSeed: Seed,
        requester: User,
        newId: CalligraphyId,
        now: Instant,
    ): Calligraphy {
        require(canCreateNext(requester)) { "캘리그래피 생성 권한이 없습니다." }

        val mergedPrompt = Prompt.of(
            listOf(prompt.value, additionalPrompt.value)
                .joinToString(separator = "\n")
                .trim()
        )

        return Calligraphy(
            id = newId,
            seed = newSeed,
            prompt = mergedPrompt,
            style = newStyle,
            user = this.user,
            result = newResult,
            createdAt = now,
            updatedAt = now,
        )
    }

    private fun canCreateNext(requester: User): Boolean =
        user.userId == requester.userId || requester.isAdmin()
}