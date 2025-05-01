package lkdcode.wanted.ecommerce.framework.common.api.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;

@Getter
@JsonPropertyOrder(value = {"success", "error"})
public class ClientErrorResponse<T> {
    private final Boolean success = false;
    private final T error;

    private ClientErrorResponse(T error) {
        this.error = error;
    }

    private static <T> ClientErrorResponse<T> from(T error) {
        return new ClientErrorResponse<>(error);
    }
}
