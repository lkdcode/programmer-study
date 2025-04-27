package run.moku.modules.users.adapter.rest.command.`in`

import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import run.moku.modules.users.adapter.rest.command.`in`.dto.UserCommandDto
import run.moku.modules.users.application.service.SignUpService

@RestController
class UserSignUpApi(
    private val signUpService: SignUpService
) {

    @PostMapping("/api/users")
    fun getSignUpApi(@RequestBody @Valid dto: UserCommandDto.SignUpDTO) = signUpService.invoke(dto.convert())
}