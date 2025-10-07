package demo.example.server.service

import demo.example.server.entity.EventHistoryJpaEntity
import demo.example.server.repository.*
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class DatabasePessimisticLockService(
    private val eventStockJpaRepository: EventStockJpaRepository,
    private val eventHistoryJpaRepository: EventHistoryJpaRepository,
) {

    @Transactional
    fun applyForUpdate(userId: Long, eventId: Long) {
        if (eventHistoryJpaRepository.existsByEventIdAndUserIdCustom(userId, eventId)) return

        val stockEntity = eventStockJpaRepository.loadByIdForUpdate(eventId)
        if (stockEntity.stock <= 0) return

        stockEntity.stock -= 1

        val history = EventHistoryJpaEntity(userId = userId, eventId = eventId)
        eventHistoryJpaRepository.save(history)
    }
}