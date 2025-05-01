package lkdcode.wanted.ecommerce.framework.common.api.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;

@Getter
@JsonPropertyOrder(value = {"success", "data", "message"})
public class ApiResponse<T> {
    private final Boolean success = true;
    private final T data;
    private final String message;

    private ApiResponse(T data, String message) {
        this.data = data;
        this.message = message;
    }

    private static <T> ApiResponse<T> ok(T data, String message) {
        return new ApiResponse<>(data, message);
    }

    private static <T> ApiResponse<T> created(T data, String message) {
        return new ApiResponse<>(data, message);
    }
}