package lkdcode.wanted.ecommerce.modules.products.domain.entity;

import java.util.Objects;

public record SellerId(
    Long value
) {
    public SellerId(Long value) {
        this.value = Objects.requireNonNull(value);
    }
}
