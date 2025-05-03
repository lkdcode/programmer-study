package lkdcode.wanted.ecommerce.modules.products.domain.value.image;

import java.util.Objects;

public record ProductAltText(
    String value
) {
    public static final int LENGTH = 255;

    public ProductAltText(String value) {
        this.value = Objects.requireNonNull(value);
        if (value.length() > LENGTH) throw new IllegalArgumentException();
    }
}
