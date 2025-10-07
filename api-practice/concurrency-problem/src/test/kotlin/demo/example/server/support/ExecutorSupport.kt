package demo.example.server.support

import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class ExecutorSupport {

    fun execute(
        concurrency: Int = 100,
        action: () -> Unit
    ) {
        val pool = Executors.newFixedThreadPool(concurrency)
        val startGate = CountDownLatch(1)
        val done = CountDownLatch(concurrency)

        try {
            repeat(concurrency) {
                pool.execute {
                    try {
                        startGate.await()
                        action()
                    } finally {
                        done.countDown()
                    }
                }
            }

            startGate.countDown()

        } finally {
            pool.shutdown()
            pool.awaitTermination(10, TimeUnit.SECONDS)
        }
    }
}