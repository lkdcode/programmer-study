package lkdcode.kiwi

import io.grpc.stub.StreamObserver
import lkdcode.grpc.kiwi.request.KiwiRequest
import lkdcode.grpc.kiwi.v2.KiwiRepositoryGrpc
import lkdcode.grpc.kiwi.v2.SavedResult
import net.devh.boot.grpc.client.inject.GrpcClient
import org.springframework.stereotype.Service

@Service
class KiwiRepository(
    @GrpcClient("kiwi")
    private val kiwiRepositoryGrpc: KiwiRepositoryGrpc.KiwiRepositoryBlockingStub,

    @GrpcClient("kiwi")
    private val kiwiRepositoryGrpcStub: KiwiRepositoryGrpc.KiwiRepositoryStub,

    @GrpcClient("kiwi")
    private val kiwiRepositoryGrpcFutureStub: KiwiRepositoryGrpc.KiwiRepositoryFutureStub,
) {

    fun save(request: KiwiRequest) {
        println("Tomato.KiwiRepository 호출")
        kiwiRepositoryGrpc.save(request)
        println("Tomato.KiwiRepository 종료")
    }

    fun saveStub(request: KiwiRequest) {
        println("Tomato.KiwiRepository#Stub 호출")
        kiwiRepositoryGrpcStub.save(request, object : StreamObserver<SavedResult> {
            override fun onNext(p0: SavedResult?) {
                println("Stub 응답 수신!")
            }

            override fun onError(p0: Throwable?) {
                println("Stub 에러 발생")
            }

            override fun onCompleted() {
                println("Stub 호출 완료")
            }
        })
        println("Tomato.KiwiRepository#Stub 종료")
    }

    fun saveFutureStub(request: KiwiRequest) {
        println("Tomato.KiwiRepository#FutureStub 호출")
        val future = kiwiRepositoryGrpcFutureStub.save(request)
        try {
            val response = future.get() // 블로킹 방식
            println("FutureStub 응답 수신 $response")
        } catch (e: Exception) {
            println("FutureStub 에러 발생")
        }
        println("Tomato.KiwiRepository#FutureStub 종료")
    }
}