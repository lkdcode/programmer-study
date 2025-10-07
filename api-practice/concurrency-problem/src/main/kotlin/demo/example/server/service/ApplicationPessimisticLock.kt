package demo.example.server.service

interface ApplicationPessimisticLock {
    fun applyBySynchronized(userId: Long, eventId: Long)
    fun applyByReentrantLock(userId: Long, eventId: Long)
}