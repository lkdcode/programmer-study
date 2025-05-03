package lkdcode.wanted.ecommerce.modules.products.domain.value;

import java.util.Objects;

public record ProductShortDescription(
    String value
) {
    public static final int LENGTH = 500;

    public ProductShortDescription(String value) {
        this.value = Objects.requireNonNull(value);
        if (this.value.length() > LENGTH) throw new IllegalArgumentException();
    }
}