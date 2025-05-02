package lkdcode.wanted.ecommerce.modules.products.domain.value;

import lkdcode.wanted.ecommerce.modules.products.domain.spec.ProductSpec;

import java.util.Objects;

public record ProductShortDescription(
    String value
) {
    public ProductShortDescription(String value) {
        this.value = Objects.requireNonNull(value);
        if (this.value.length() > ProductSpec.SHORT_DESCRIPTION_LENGTH) throw new IllegalArgumentException();
    }
}