package lkdcode.wanted.ecommerce.modules.products.domain.value;

import java.util.Objects;

public record ProductName(
    String value
) {
    public static final int LENGTH = 255;

    public ProductName(String value) {
        this.value = Objects.requireNonNull(value);
        if (this.value.length() > LENGTH) throw new IllegalArgumentException();
    }
}