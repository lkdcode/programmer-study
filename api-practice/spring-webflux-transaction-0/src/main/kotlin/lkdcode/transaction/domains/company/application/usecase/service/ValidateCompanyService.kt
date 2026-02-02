package lkdcode.transaction.domains.company.application.usecase.service

import lkdcode.transaction.domains.company.application.usecase.ValidateCompanyUsecase
import lkdcode.transaction.domains.company.domain.model.ValidateCompanyModel
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import java.time.Duration

@Service
class ValidateCompanyService : ValidateCompanyUsecase {

    override fun validate(model: ValidateCompanyModel): Mono<Void> =
        Mono.delay(Duration.ofMillis(500))  // 외부 API 호출 시뮬레이션 (500ms)
            .then(
                Mono.fromCallable {
                    // 사업자 등록번호 형식 검증 (간단한 예시)
                    if (!model.brn.matches(Regex("^\\d{10}$"))) {
                        throw IllegalArgumentException("유효하지 않은 사업자 등록번호입니다.")
                    }
                }
            )
            .then()
}