package lkdcode.grpc.service.v2

import io.grpc.stub.StreamObserver
import lkdcode.grpc.kiwi.request.KiwiRequest
import lkdcode.grpc.kiwi.v2.KiwiRepositoryGrpc
import lkdcode.grpc.kiwi.v2.SavedResult
import net.devh.boot.grpc.server.service.GrpcService

@GrpcService
class KiwiRepository : KiwiRepositoryGrpc.KiwiRepositoryImplBase() {

    override fun save(
        request: KiwiRequest,
        responseObserver: StreamObserver<SavedResult>
    ) {
        println("V2. KiwiRepository 호출")
        println("... 저장")
        responseObserver.onNext(
            SavedResult.newBuilder()
                .setId(1L)
                .build()
        )
        responseObserver.onCompleted()
        println("V2. KiwiRepository 종료")
    }
}