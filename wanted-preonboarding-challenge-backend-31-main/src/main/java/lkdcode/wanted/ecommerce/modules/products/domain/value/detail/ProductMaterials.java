package lkdcode.wanted.ecommerce.modules.products.domain.value.detail;

import java.util.Objects;

public record ProductMaterials(
    String value
) {
    public ProductMaterials(String value) {
        this.value = Objects.requireNonNull(value);
    }
}