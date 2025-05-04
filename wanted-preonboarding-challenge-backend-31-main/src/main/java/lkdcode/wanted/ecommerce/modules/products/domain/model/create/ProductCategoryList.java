package lkdcode.wanted.ecommerce.modules.products.domain.model.create;

import java.util.List;
import java.util.Objects;

public record ProductCategoryList(
    List<ProductCategoryModel> list
) {
    public ProductCategoryList(List<ProductCategoryModel> list) {
        this.list = Objects.requireNonNull(list);
    }
}