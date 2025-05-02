package lkdcode.wanted.ecommerce.modules.products.domain.entity;

import java.util.Objects;

public record BrandId(
    Long value
) {
    public BrandId(Long value) {
        this.value = Objects.requireNonNull(value);
    }
}
