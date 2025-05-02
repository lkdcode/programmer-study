package lkdcode.wanted.ecommerce.modules.products.domain.value;

import lkdcode.wanted.ecommerce.modules.products.domain.spec.ProductSpec;

import java.util.Objects;

public record ProductName(
    String value
) {
    public ProductName(String value) {
        this.value = Objects.requireNonNull(value);
        if (this.value.length() > ProductSpec.NAME_LENGTH) throw new IllegalArgumentException();
    }
}