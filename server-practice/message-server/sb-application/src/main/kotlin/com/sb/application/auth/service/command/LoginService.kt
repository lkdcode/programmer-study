package com.sb.application.auth.service.command

import com.sb.application.auth.ports.input.command.LoginUsecase
import com.sb.application.user.ports.output.command.UserCommandPort
import com.sb.application.user.ports.output.query.UserQueryPort
import com.sb.domain.user.entity.User
import com.sb.domain.user.value.Email
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
@Transactional
class LoginService(
    private val userCommandPort: UserCommandPort,
    private val userQueryPort: UserQueryPort,
) : LoginUsecase {

    override suspend fun onLoginSuccess(userId: User.UserId) {
        val user = userQueryPort
            .loadById(userId)
            .succeedLogin()
            .recordLoginSuccess()

        userCommandPort.update(user)
    }

    override suspend fun onLoginSuccess(email: Email) {
        val user = userQueryPort
            .loadByEmail(email)
            .succeedLogin()
            .recordLoginSuccess()

        userCommandPort.update(user)
    }

    override suspend fun onLoginFail(userId: User.UserId) {
        val user = userQueryPort
            .loadById(userId)
            .failLogin()

        userCommandPort.update(user)
    }

    override suspend fun onLoginFail(email: Email) {
        val user = userQueryPort
            .loadByEmail(email)
            .failLogin()

        userCommandPort.update(user)
    }
}