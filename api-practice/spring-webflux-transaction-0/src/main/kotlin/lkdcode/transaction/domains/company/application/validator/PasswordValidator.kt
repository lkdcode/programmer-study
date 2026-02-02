package lkdcode.transaction.domains.company.application.validator

import lkdcode.transaction.domains.company.domain.model.Password
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class PasswordValidator {

    fun validatePasswordPattern(password: String): Mono<Void> =
        Mono.fromCallable {
            if (!password.matches(Password.PATTERN)) {
                throw IllegalArgumentException("비밀번호는 대문자, 소문자, 숫자, 특수문자를 포함해야 합니다.")
            }
        }.then()

    fun validatePasswordMatch(password: String, passwordConfirm: String): Mono<Void> =
        Mono.fromCallable {
            if (password != passwordConfirm) {
                throw IllegalArgumentException("비밀번호가 일치하지 않습니다.")
            }
        }.then()
}