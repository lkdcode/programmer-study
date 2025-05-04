package lkdcode.wanted.ecommerce.modules.products.domain.value.tag;

import java.util.List;
import java.util.Objects;

public record ProductTagList(
    List<ProductTag> list
) {

    public ProductTagList(List<ProductTag> list) {
        this.list = Objects.requireNonNull(list);
    }
}