package lkdcode.wanted.ecommerce.modules.products.domain.value.option;

import java.util.List;
import java.util.Objects;

public record ProductOptionGroupDisplayOrderList(
    List<ProductOptionGroupDisplayOrder> list
) {

    public ProductOptionGroupDisplayOrderList(List<ProductOptionGroupDisplayOrder> list) {
        this.list = Objects.requireNonNull(list);
    }
}
