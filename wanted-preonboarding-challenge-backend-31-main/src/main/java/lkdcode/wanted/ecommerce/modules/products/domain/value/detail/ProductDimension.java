package lkdcode.wanted.ecommerce.modules.products.domain.value.detail;

import java.util.Map;
import java.util.Objects;

public record ProductDimension(
    Map<String, Objects> value
) {
    public ProductDimension(Map<String, Objects> value) {
        this.value = Objects.requireNonNull(value);
    }
}
