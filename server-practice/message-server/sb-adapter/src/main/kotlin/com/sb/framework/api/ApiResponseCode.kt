package com.sb.framework.api

import com.sb.domain.user.value.Password
import org.springframework.http.HttpStatus

enum class ApiResponseCode(
    val code: String,
    val status: HttpStatus,
    val message: String,
) {

    OK("S001", HttpStatus.OK, "리소스 요청에 성공했습니다."),
    CREATED("S002", HttpStatus.CREATED, "리소스 생성 요청에 성공했습니다."),
    UPDATED("S003", HttpStatus.OK, "리소스 수정 요청에 성공했습니다."),
    DELETED("S004", HttpStatus.OK, "리소스 삭제 요청에 성공했습니다."),
    SUCCESS_CREDENTIALS("S005", HttpStatus.OK, "인증에 성공했습니다."),
    DISCONNECT("S006", HttpStatus.OK, "연결이 종료되었습니다."),

    /* REQUEST */
    REQUEST_INVALID("CC001", HttpStatus.BAD_REQUEST, "유효성 검증에 실패했습니다."),
    REQUEST_INVALID_DATA("CC002", HttpStatus.BAD_REQUEST, "유효하지 않은 데이터입니다."),
    REQUEST_INVALID_BODY("CC003", HttpStatus.BAD_REQUEST, "요청 바디가 잘못되었습니다."),
    REQUEST_MISSING_HEADER("CC004", HttpStatus.BAD_REQUEST, "필수 헤더가 누락되었습니다."),
    REQUEST_UNSUPPORTED_METHOD("CC005", HttpStatus.BAD_REQUEST, "지원하지 않는 HTTP 메서드입니다."),
    REQUEST_UNSUPPORTED_REQUEST("CC006", HttpStatus.BAD_REQUEST, "지원하지 않는 요청입니다."),

    /* SECURITY */
    INVALID_CREDENTIALS("SC001", HttpStatus.UNAUTHORIZED, "인증에 실패했습니다."),
    AUTHENTICATION_REQUIRED("SC002", HttpStatus.UNAUTHORIZED, "인증되지 않았습니다."),
    ACCESS_DENIED("SC003", HttpStatus.FORBIDDEN, "권한이 없습니다."),
    ACCOUNT_LOCKED("SC004", HttpStatus.LOCKED, "로그인 시도 횟수를 초과하여 계정이 잠겼습니다."),
    INVALID_REFRESH_TOKEN("SC005", HttpStatus.UNAUTHORIZED, "인증에 실패했습니다."),
    INVALID_OPERATION_TYPE("SC006", HttpStatus.FORBIDDEN, "올바르지 않은 접근입니다."),
    INVALID("SC007", HttpStatus.UNAUTHORIZED, "올바르지 않은 접근입니다."),

    /* TERMS */
    TERMS_NOT_FOUND("TER001", HttpStatus.NOT_FOUND, "해당 약관을 찾을 수 없습니다."),
    TERMS_REQUIRED_WHEN_SIGN_UP("TER002", HttpStatus.BAD_REQUEST, "필수 약관에 동의해야 합니다."),
    TERMS_REQUIRED_WHEN_CREATE_USER("TER003", HttpStatus.BAD_REQUEST, "필수 약관에 동의해야 합니다."),

    /* PLAN */
    PLAN_NOT_FOUND("PLN001", HttpStatus.NOT_FOUND, "해당 플랜을 찾을 수 없습니다."),

    /* FEEDBACK */
    FEEDBACK_NOT_FOUND("FEB001", HttpStatus.NOT_FOUND, "해당 피드백을 찾을 수 없습니다."),
    FEEDBACK_INVALID_OWNER("FEB002", HttpStatus.FORBIDDEN, "삭제 권한이 없습니다."),

    /* HIS_PLAN */
    HIS_PLAN_NOT_FOUND("HPL001", HttpStatus.NOT_FOUND, "해당 플랜을 찾을 수 없습니다."),

    /* USER */
    DUPLICATE_LOGIN_ID("U001", HttpStatus.CONFLICT, "이미 존재하는 로그인 아이디입니다."),
    PASSWORD_MISMATCH("U002", HttpStatus.BAD_REQUEST, "비밀번호와 재확인 비밀번호가 일치하지 않습니다."),
    INVALID_PASSWORD("U003", HttpStatus.BAD_REQUEST, Password.POLICY_MESSAGE),
    USER_INVALID("U004", HttpStatus.BAD_REQUEST, "올바르지 않은 접근입니다."),
    REQUIRED_PASSWORD_CONFIRM("U005", HttpStatus.BAD_REQUEST, "재확인 비밀번호는 필수입니다."),
    USER_NOT_FOUND("U006", HttpStatus.BAD_REQUEST, "해당 유저를 찾을 수 없습니다."),

    /* ADMIN */
    ADMIN_NOT_FOUND_BY_ID("A001", HttpStatus.NOT_FOUND, "존재하지 않는 관리자입니다."),
    ADMIN_CAN_NOT_DELETE_SELF("A002", HttpStatus.BAD_REQUEST, "본인을 삭제할 수 없습니다. 다른 관리자 계정을 사용해 주세요."),

    /* WEB_CLIENT */
    WEB_CLIENT_IS_4XX_ERROR("RC001", HttpStatus.BAD_REQUEST, "서버에서 외부 API 호출에 실패했습니다."),
    WEB_CLIENT_IS_5XX_ERROR("RC002", HttpStatus.INTERNAL_SERVER_ERROR, "서버에서 외부 API 호출에 실패했습니다."),


    /* SERVER */
    SEVER_SQL_EXCEPTION("SV001", HttpStatus.INTERNAL_SERVER_ERROR, "서버에서 에러가 발생했습니다."),
    SEVER_DATABASE_EXCEPTION("SV002", HttpStatus.INTERNAL_SERVER_ERROR, "서버에서 에러가 발생했습니다."),
    SEVER_DATA_INTEGRITY_VIOLATION_EXCEPTION("SV003", HttpStatus.INTERNAL_SERVER_ERROR, "서버에서 에러가 발생했습니다."),
    SEVER_TRANSIENT_RESOURCE_EXCEPTION("SV004", HttpStatus.INTERNAL_SERVER_ERROR, "서버에서 에러가 발생했습니다."),
    SEVER_NON_TRANSIENT_RESOURCE_EXCEPTION("SV005", HttpStatus.INTERNAL_SERVER_ERROR, "서버에서 에러가 발생했습니다."),
    SEVER_ROLL_BACK_EXCEPTION("SV006", HttpStatus.INTERNAL_SERVER_ERROR, "서버에서 에러가 발생했습니다."),

    SEVER_UNHANDLED_EXCEPTION("SV998", HttpStatus.INTERNAL_SERVER_ERROR, "서버에서 에러가 발생했습니다."),
    SEVER_CRITICAL_EXCEPTION("SV999", HttpStatus.INTERNAL_SERVER_ERROR, "서버에서 에러가 발생했습니다."),
    I_AM_A_TEAPOT("HELLO_WORLD", HttpStatus.I_AM_A_TEAPOT, "I_AM_A_TEAPOT"),

    ;

    companion object {
        fun findByCode(code: String): ApiResponseCode? =
            entries.firstOrNull { it.code == code }
    }
}