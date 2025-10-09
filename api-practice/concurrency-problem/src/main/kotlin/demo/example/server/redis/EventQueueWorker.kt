package demo.example.server.redis

import demo.example.server.redis.RedisEvent.KEY
import demo.example.server.service.EventWithoutLockService
import jakarta.annotation.PostConstruct
import jakarta.annotation.PreDestroy
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Service

@Service
class EventQueueWorker(
    private val redis: StringRedisTemplate,
    private val eventWithoutLockService: EventWithoutLockService,
) {
    @Volatile
    private var running = true
    private lateinit var worker: Thread

    @PostConstruct
    fun start() {
        worker = Thread { run() }.apply {
            isDaemon = true
            name = "lkdcode-event-queue-worker-1"
            start()
        }
    }

    @PreDestroy
    fun stop() {
        running = false
        worker.interrupt()
    }

    fun waitQueueEmpty() {
        while (true) {
            val size = redis.opsForList().size(KEY)
            if (size == null || size == 0L) return
        }
    }

    private fun run() {
        val ops = redis.opsForList()
        try {
            while (running) {
                ops.rightPop(KEY)
                    ?.let {
                        val (userId, eventId) = parse(it)
                        eventWithoutLockService.apply(userId, eventId)
                    }
            }
        } catch (_: Exception) {
        }
    }

    private fun parse(json: String): Pair<Long, Long> {
        val u = """"userId":(\d+)""".toRegex().find(json)!!.groupValues[1].toLong()
        val e = """"eventId":(\d+)""".toRegex().find(json)!!.groupValues[1].toLong()
        return u to e
    }
}