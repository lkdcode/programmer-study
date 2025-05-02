package lkdcode.wanted.ecommerce.modules.products.domain.value;

import lkdcode.wanted.ecommerce.modules.products.domain.spec.ProductSpec;

import java.util.Objects;

public record ProductSlug(
    String value
) {
    public ProductSlug(String value) {
        this.value = Objects.requireNonNull(value);
        if (this.value.length() > ProductSpec.SLUG_LENGTH) throw new IllegalArgumentException();
    }
}