package lkdcode.wanted.ecommerce.framework.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;


@Getter
@RequiredArgsConstructor
public enum ApplicationResponseCode {

    // SUCCESS
    SUCCESS_OK("SUCCESS", OK, "리소스 요청에 성공한 경우"),

    FAIL("FAIL", BAD_REQUEST, "리소스 요청에 실패한 경우"),
    ;
    private final String code;
    private final HttpStatus httpStatus;
    private final String description;
}
