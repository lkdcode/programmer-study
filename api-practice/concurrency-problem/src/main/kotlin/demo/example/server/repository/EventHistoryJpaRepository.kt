package demo.example.server.repository

import demo.example.server.entity.EventHistoryJpaEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface EventHistoryJpaRepository : JpaRepository<EventHistoryJpaEntity, Long> {
    fun findAllByEventId(eventId: Long): List<EventHistoryJpaEntity>
    fun findAllByUserIdInAndEventId(userIdList: List<Long>, eventId: Long): List<EventHistoryJpaEntity>

    @Query(
        value = "SELECT EXISTS (SELECT 1 FROM event_history WHERE user_id = :userId AND event_id = :eventId LIMIT 1)",
        nativeQuery = true
    )
    fun existsByEventIdAndUserIdRaw(
        @Param("userId") userId: Long,
        @Param("eventId") eventId: Long
    ): Int

    @Query(
        value = "SELECT COUNT(*) FROM event_history WHERE event_id = :eventId",
        nativeQuery = true
    )
    fun countByEventIdRaw(
        @Param("eventId") eventId: Long
    ): Int
}

fun EventHistoryJpaRepository.existsByEventIdAndUserIdCustom(userId: Long, eventId: Long): Boolean =
    existsByEventIdAndUserIdRaw(userId, eventId) > 0