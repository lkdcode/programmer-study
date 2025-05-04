package lkdcode.wanted.ecommerce.modules.products.domain.model.create;

import lkdcode.wanted.ecommerce.modules.products.domain.value.option.ProductOptionGroupDisplayOrderList;

import java.util.List;
import java.util.Objects;

public record ProductOptionGroupList(
    List<ProductOptionGroupModel> list
) {

    public ProductOptionGroupList(List<ProductOptionGroupModel> list) {
        this.list = Objects.requireNonNull(list);
    }

    public ProductOptionGroupDisplayOrderList getProductOptionGroupDisplayOrderList() {
        return new ProductOptionGroupDisplayOrderList(
            list.stream()
                .map(ProductOptionGroupModel::groupDisplayOrder)
                .toList()
        );
    }
}