package com.sb.application.auth.ports.output

import com.sb.application.auth.dto.IssueLoginTokensCommand
import com.sb.application.auth.dto.TokenPair


interface TokenIssuerPort {
    suspend fun issue(command: IssueLoginTokensCommand): TokenPair
}