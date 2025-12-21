package com.sb.application.auth.service

import com.sb.application.auth.dto.IssueLoginTokensCommand
import com.sb.application.auth.dto.TokenPair
import com.sb.application.auth.ports.input.command.IssueLoginTokensUsecase
import com.sb.application.auth.ports.output.command.TokenIssuerPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class IssueLoginTokensService(
    private val tokenIssuerPort: TokenIssuerPort,
) : IssueLoginTokensUsecase {

    override suspend fun issue(command: IssueLoginTokensCommand): TokenPair =
        tokenIssuerPort.issue(command)
}