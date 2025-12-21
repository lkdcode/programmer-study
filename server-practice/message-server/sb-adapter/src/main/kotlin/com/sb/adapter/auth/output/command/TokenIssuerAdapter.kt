package com.sb.adapter.auth.output.command

import com.sb.application.auth.dto.IssueLoginTokensCommand
import com.sb.application.auth.dto.TokenPair
import com.sb.application.auth.ports.output.command.RefreshTokenStorePort
import com.sb.application.auth.ports.output.command.TokenIssuerPort
import com.sb.framework.security.jwt.reactive.ReactiveJwtService
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.stereotype.Component

@Component
class TokenIssuerAdapter(
    private val jwtService: ReactiveJwtService,
    private val refreshTokenStorePort: RefreshTokenStorePort,
) : TokenIssuerPort {

    override suspend fun issue(command: IssueLoginTokensCommand): TokenPair {
        val tokens = jwtService
            .issueTokens(
                username = command.email,
                userRole = command.role,
            )
            .awaitSingle()

        refreshTokenStorePort.save(
            userId = command.userId,
            refreshToken = tokens.refreshToken,
            expiredAt = tokens.refreshTokenExpiresAt,
        )

        return tokens
    }
}