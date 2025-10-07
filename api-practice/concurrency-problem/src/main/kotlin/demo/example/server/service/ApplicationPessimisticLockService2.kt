package demo.example.server.service

import org.springframework.stereotype.Service
import java.util.concurrent.locks.ReentrantLock

@Service
class ApplicationPessimisticLockService2(
    private val eventWithoutLockService: EventWithoutLockService,
) : ApplicationPessimisticLock {
    private val lock = ReentrantLock()
    private val monitor = Any()

    override fun applyBySynchronized(userId: Long, eventId: Long) {
        synchronized(monitor) {
            eventWithoutLockService.apply(userId, eventId)
        }
    }

    override fun applyByReentrantLock(userId: Long, eventId: Long) {
        lock.lock()

        try {
            eventWithoutLockService.apply(userId, eventId)
        } finally {
            lock.unlock()
        }
    }
}