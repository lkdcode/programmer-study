package lkdcode.wanted.ecommerce.framework.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApplicationException extends RuntimeException {
    private final ApplicationResponseCode responseCode;

    public ApplicationException(ApplicationResponseCode responseCode) {
        super(responseCode.name());
        this.responseCode = responseCode;
    }

    public HttpStatus getHttpStatus() {
        return this.responseCode.getHttpStatus();
    }
}