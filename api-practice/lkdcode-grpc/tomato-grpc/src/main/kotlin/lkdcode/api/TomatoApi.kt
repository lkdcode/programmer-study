package lkdcode.api

import com.google.protobuf.Timestamp
import lkdcode.grpc.kiwi.request.KiwiRequest
import lkdcode.kiwi.KiwiRepository
import lkdcode.kiwi.KiwiService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime
import java.time.ZoneId

@RestController
class TomatoApi(
    private val kiwiService: KiwiService,
    private val kiwiRepository: KiwiRepository
) {

    @PostMapping("/api/tomato")
    fun getCreate(
        @RequestBody request: FruitRequest
    ) {
        println("TomatoApi 호출")
        val kiwiRequest = request.convert()
        kiwiService.validate(kiwiRequest)
        kiwiRepository.save(kiwiRequest)
        println("TomatoApi 종료")
    }
}

data class FruitRequest(
    val name: String,
    val quantity: Long,
    val harvestedAt: LocalDateTime
) {
    fun convert(): KiwiRequest =
        KiwiRequest.newBuilder()
            .setName(name)
            .setQuantity(quantity)
            .setHarvestedAt(harvestedAt.toProtoTimestamp())
            .build()

}

fun LocalDateTime.toProtoTimestamp(zone: ZoneId = ZoneId.of("Asia/Seoul")): Timestamp {
    val instant = this.atZone(zone).toInstant()
    return Timestamp.newBuilder()
        .setSeconds(instant.epochSecond)
        .setNanos(instant.nano)
        .build()
}