package demo.example.server.service

import demo.example.server.entity.EventHistoryJpaEntity
import demo.example.server.repository.EventHistoryJpaRepository
import demo.example.server.repository.EventStockJpaRepository
import demo.example.server.repository.existsByEventIdAndUserIdCustom
import demo.example.server.repository.loadById
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ApplicationCASService(
    private val eventStockJpaRepository: EventStockJpaRepository,
    private val eventHistoryJpaRepository: EventHistoryJpaRepository,
) {

    @Transactional
    fun apply(userId: Long, eventId: Long) {
        if (eventHistoryJpaRepository.existsByEventIdAndUserIdCustom(userId, eventId)) return

        val stockEntity = eventStockJpaRepository.loadById(eventId)
        if (stockEntity.stock <= 0) return

        val result = eventStockJpaRepository.tryDecrement(eventId, stockEntity.stock)

        if (result == 1) {
            val history = EventHistoryJpaEntity(userId = userId, eventId = eventId)
            eventHistoryJpaRepository.save(history)
        }
    }
}