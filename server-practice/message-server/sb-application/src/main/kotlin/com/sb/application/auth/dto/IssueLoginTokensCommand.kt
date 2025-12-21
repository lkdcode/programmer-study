package com.sb.application.auth.dto

data class IssueLoginTokensCommand(
    val userId: Long,
    val email: String,
    val role: String,
)