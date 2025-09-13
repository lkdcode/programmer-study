package lkdcode.kiwi

import lkdcode.grpc.kiwi.request.KiwiRequest
import lkdcode.grpc.kiwi.v1.KiwiServiceGrpc
import net.devh.boot.grpc.client.inject.GrpcClient
import org.springframework.stereotype.Service

@Service
class KiwiService(
    @GrpcClient("kiwi")
    private val kiwiServiceGrpc: KiwiServiceGrpc.KiwiServiceBlockingStub,
) {

    fun validate(request: KiwiRequest) {
        println("Tomato.KiwiService 호출")
        kiwiServiceGrpc.validate(request)
        println("Tomato.KiwiService 종료")
    }
}