package run.moku.framework.api.response

import org.springframework.http.HttpStatus
import run.moku.modules.gomoku.domain.value.board.MokuBoard

enum class ApiResponseCode(
    val code: String,
    val status: HttpStatus,
    val message: String,
) {

    OK("S001", HttpStatus.OK, "리소스 요청에 성공했습니다."),
    CREATED("S002", HttpStatus.CREATED, "리소스 생성에 성공했습니다."),
    DELETED("S003", HttpStatus.NO_CONTENT, "리소스 삭제 요청에 성공했습니다."),


    /* SECURITY */
    INVALID_CREDENTIALS("SC001", HttpStatus.UNAUTHORIZED, "로그인에 실패했습니다."),


    /* CLIENT */
    CLIENT_INVALID_REQUEST("CC001", HttpStatus.BAD_REQUEST, "유효성 검증에 실패했습니다."),
    CLIENT_INVALID_DATA("CC002", HttpStatus.BAD_REQUEST, "유효하지 않은 데이터입니다."),
    CLIENT_INVALID_BODY("CC003", HttpStatus.BAD_REQUEST, "요청 바디가 잘못되었습니다."),
    CLIENT_MISSING_HEADER("CC004", HttpStatus.BAD_REQUEST, "필수 헤더가 누락되었습니다."),
    CLIENT_UNSUPPORTED_METHOD("CC005", HttpStatus.BAD_REQUEST, "지원하지 않는 HTTP 메서드입니다."),
    CLIENT_UNSUPPORTED_REQUEST("CC006", HttpStatus.BAD_REQUEST, "지원하지 않는 요청입니다."),


    /* USER */
    DUPLICATE_LOGIN_ID("U001", HttpStatus.CONFLICT, "이미 존재하는 로그인 아이디입니다."),
    DUPLICATE_NICKNAME("U002", HttpStatus.CONFLICT, "이미 존재하는 닉네임입니다."),
    NOT_FOUND_USER("U003", HttpStatus.NOT_FOUND, "해당 유저를 찾을 수 없습니다."),
    MISMATCH_PASSWORD_AND_PASSWORD_CONFIRM("U004", HttpStatus.BAD_REQUEST, "비밀번호와 재확인 비밀번호가 일치하지 않습니다."),

    /* Board */
    BOARD_INVALID_ROW_SIZE("B001", HttpStatus.BAD_REQUEST,"바둑판 사이즈는 ${MokuBoard.DEFAULT_INDEX}x${MokuBoard.DEFAULT_INDEX} 입니다."),
    BOARD_INVALID_COL_SIZE("B002", HttpStatus.BAD_REQUEST,"바둑판 사이즈는 ${MokuBoard.DEFAULT_INDEX}x${MokuBoard.DEFAULT_INDEX} 입니다."),


    /* MOKU */
    MOKU_INVALID_TURN("M001", HttpStatus.BAD_REQUEST,"차례가 아닙니다."),

    /* SERVER */
    SEVER_SQL_EXCEPTION("SE001", HttpStatus.INTERNAL_SERVER_ERROR, "데이터베이스 SQL 에러가 발생했습니다."),
    SEVER_DATABASE_EXCEPTION("SE002", HttpStatus.INTERNAL_SERVER_ERROR, "데이터베이스 에러가 발생했습니다."),
    SEVER_UNHANDLED_EXCEPTION("SE999", HttpStatus.INTERNAL_SERVER_ERROR, "서버에서 에러가 발생했습니다."),
    SEVER_CRITICAL_EXCEPTION("SE999", HttpStatus.INTERNAL_SERVER_ERROR, "서버에서 에러가 발생했습니다."),
}