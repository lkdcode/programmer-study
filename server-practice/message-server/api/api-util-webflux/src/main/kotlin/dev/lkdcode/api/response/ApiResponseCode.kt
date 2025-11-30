package dev.lkdcode.api.response

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


    /* REQUEST */
    REQUEST_INVALID("RI001", HttpStatus.BAD_REQUEST, "유효성 검증에 실패했습니다."),
    REQUEST_INVALID_DATA("RI002", HttpStatus.BAD_REQUEST, "유효하지 않은 데이터입니다."),
    REQUEST_INVALID_BODY("RI003", HttpStatus.BAD_REQUEST, "요청 바디가 잘못되었습니다."),
    REQUEST_MISSING_HEADER("RI004", HttpStatus.BAD_REQUEST, "필수 헤더가 누락되었습니다."),
    REQUEST_UNSUPPORTED_METHOD("RI005", HttpStatus.BAD_REQUEST, "지원하지 않는 HTTP 메서드입니다."),
    REQUEST_UNSUPPORTED_REQUEST("RI006", HttpStatus.BAD_REQUEST, "지원하지 않는 요청입니다."),


    /* SECURITY */
    SUCCESS_CREDENTIALS("SC001", HttpStatus.OK, "인증에 성공했습니다."),
    INVALID_CREDENTIALS("SC002", HttpStatus.UNAUTHORIZED, "인증에 실패했습니다."),
    AUTHENTICATION_REQUIRED("SC003", HttpStatus.UNAUTHORIZED, "인증되지 않았습니다."),
    ACCESS_DENIED("SC004", HttpStatus.FORBIDDEN, "권한이 없습니다."),
    ACCOUNT_LOCKED("SC005", HttpStatus.LOCKED, "로그인 시도 횟수를 초과하여 계정이 잠겼습니다."),
    INVALID_REFRESH_TOKEN("SC006", HttpStatus.UNAUTHORIZED, "인증에 실패했습니다."),
    INVALID_OPERATION_TYPE("SC007", HttpStatus.FORBIDDEN, "올바르지 않은 접근입니다."),


    /* WEB_CLIENT */
    WEB_CLIENT_IS_4XX_ERROR("RC001", HttpStatus.BAD_REQUEST, "외부 API 호출에 실패했습니다."),
    WEB_CLIENT_IS_5XX_ERROR("RC002", HttpStatus.INTERNAL_SERVER_ERROR, "외부 API 호출에 실패했습니다."),


    /* FILE */
    FILE_CREATE_FAIL("F001", HttpStatus.BAD_REQUEST, "파일 저장에 실패했습니다."),
    FILE_DELETE_FAIL("F002", HttpStatus.BAD_REQUEST, "파일 삭제에 실패했습니다."),


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