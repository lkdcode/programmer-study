package demo.example.server.repository

import demo.example.server.entity.EventStockJpaEntity
import jakarta.persistence.LockModeType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param


interface EventStockJpaRepository : JpaRepository<EventStockJpaEntity, Long> {
    // SELECT ... FOR UPDATE (DB가 락을 잡음)
    @Lock(LockModeType.PESSIMISTIC_WRITE)
//    @QueryHints(
//        // 잠금 대기 시간(ms) — 0이면 바로 실패, -2(Hibernate)면 기본값
//        // DB/드라이버에 따라 적용이 다를 수 있음
//        QueryHint(name = "jakarta.persistence.lock.timeout", value = "5000")
//    )
    @Query("SELECT s FROM EventStockJpaEntity s WHERE s.id = :id")
    fun findByIdForUpdate(@Param("id") id: Long): EventStockJpaEntity?

}

fun EventStockJpaRepository.loadById(id: Long): EventStockJpaEntity =
    findById(id).orElseThrow()

fun EventStockJpaRepository.loadByIdForUpdate(id: Long): EventStockJpaEntity =
    findByIdForUpdate(id) ?: throw IllegalArgumentException()