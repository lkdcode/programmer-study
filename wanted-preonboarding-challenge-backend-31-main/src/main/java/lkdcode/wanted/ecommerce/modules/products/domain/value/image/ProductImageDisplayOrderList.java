package lkdcode.wanted.ecommerce.modules.products.domain.value.image;

import java.util.List;
import java.util.Objects;

public record ProductImageDisplayOrderList(
    List<ProductImageDisplayOrder> list
) {

    public ProductImageDisplayOrderList(List<ProductImageDisplayOrder> list) {
        this.list = Objects.requireNonNull(list);
    }
}