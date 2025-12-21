package com.sb.application.auth.ports.input.command

import com.sb.application.auth.dto.IssueLoginTokensCommand
import com.sb.application.auth.dto.TokenPair

interface IssueLoginTokensUsecase {
    suspend fun issue(command: IssueLoginTokensCommand): TokenPair
}