package com.sb.domain.user.exception

import com.sb.domain.exception.DomainErrorCode

enum class UserErrorCode(
    override val code: String,
    override val message: String,
) : DomainErrorCode {
    EMAIL_REQUIRED("USR001", "이메일은 필수입니다."),
    EMAIL_INVALID("USR002", "올바른 이메일 형식이 아닙니다."),

    NICKNAME_REQUIRED("USR003", "닉네임은 필수입니다."),
    NICKNAME_INVALID_LENGTH("USR004", "닉네임 길이가 올바르지 않습니다."),

    OAUTH_SUBJECT_REQUIRED("USR005", "OAuth subject는 필수입니다."),
    OAUTH_SUBJECT_INVALID_LENGTH("USR006", "OAuth subject 길이가 올바르지 않습니다."),

    EMAIL_VERIFICATION_TOKEN_REQUIRED("USR007", "이메일 인증 토큰은 필수입니다."),
    EMAIL_VERIFICATION_TOKEN_INVALID_LENGTH("USR008", "이메일 인증 토큰 길이가 올바르지 않습니다."),
    EMAIL_VERIFICATION_TOKEN_INVALID_FORMAT("USR009", "이메일 인증 토큰 형식이 올바르지 않습니다."),

    PASSWORD_REQUIRED("USR010", "비밀번호는 필수입니다."),
    PASSWORD_INVALID_LENGTH("USR011", "비밀번호 길이가 올바르지 않습니다."),
    PASSWORD_POLICY("USR012", "비밀번호 정책을 만족해야 합니다."),
    PASSWORD_WHITESPACE("USR013", "비밀번호에는 공백을 포함할 수 없습니다."),

    EMAIL_VERIFICATION_ALREADY_PROCESSED("USR014", "이미 처리된 이메일 인증입니다."),
    EMAIL_VERIFICATION_TOKEN_MISMATCH("USR015", "이메일 인증 토큰이 올바르지 않습니다."),
    EMAIL_VERIFICATION_EXPIRED("USR016", "이메일 인증이 만료되었습니다."),
    EMAIL_VERIFICATION_NOT_VERIFIED("USR017", "이메일 인증이 완료되지 않았습니다."),

    DUPLICATE_EMAIL("USR018", "이미 등록된 이메일입니다."),

    PASSWORD_CONFIRM_MISMATCH("USR019", "비밀번호와 재확인 비밀번호가 일치하지 않습니다."),
    USER_NOT_FOUND("USR020", "해당 유저를 찾을 수 없습니다."),
    AUTHENTICATION_FAILED("USR021", "인증에 실패했습니다."),
    ACCESS_DENIED("USR022", "권한이 없습니다.")
    ;
}