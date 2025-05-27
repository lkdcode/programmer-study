package run.moku.framework.time


import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

object TimeUtil {
    private val KST: ZoneId = ZoneId.of("Asia/Seoul")
    fun nowKST(): LocalDateTime = Instant.now()
        .atZone(KST)
        .toLocalDateTime()
}