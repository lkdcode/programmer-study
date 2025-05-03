package lkdcode.wanted.ecommerce.modules.products.domain.value.category;

import java.util.Objects;

public record ProductCategoryIsPrimary(
    Boolean isPrimary
) {
    public static final boolean DEFAULT = false;

    public ProductCategoryIsPrimary(Boolean isPrimary) {
        this.isPrimary = Objects.requireNonNull(isPrimary);
    }

    public static ProductCategoryIsPrimary init() {
        return new ProductCategoryIsPrimary(DEFAULT);
    }
}