package lkdcode.wanted.ecommerce.modules.products.domain.value.image;

import java.util.Objects;

public record ProductImageIsPrimary(
    Boolean isPrimary
) {
    public static final boolean DEFAULT = false;

    public ProductImageIsPrimary(Boolean isPrimary) {
        this.isPrimary = Objects.requireNonNull(isPrimary);
    }

    public static ProductImageIsPrimary init() {
        return new ProductImageIsPrimary(DEFAULT);
    }
}