package lkdcode.grpc.service.v1

import com.google.protobuf.Empty
import io.grpc.stub.StreamObserver
import lkdcode.grpc.kiwi.request.KiwiRequest
import lkdcode.grpc.kiwi.v1.KiwiServiceGrpc
import net.devh.boot.grpc.server.service.GrpcService

@GrpcService
class KiwiService : KiwiServiceGrpc.KiwiServiceImplBase() {

    override fun validate(
        request: KiwiRequest,
        responseObserver: StreamObserver<Empty>
    ) {
        println("V1. KiwiGrpcService 호출")
        println("... 검증")

        responseObserver.onNext(Empty.getDefaultInstance())
        responseObserver.onCompleted()
        println("V1. KiwiGrpcService 종료")
    }
}