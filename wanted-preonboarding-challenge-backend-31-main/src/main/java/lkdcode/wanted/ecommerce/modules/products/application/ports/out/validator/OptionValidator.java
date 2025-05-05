package lkdcode.wanted.ecommerce.modules.products.application.ports.out.validator;

import lkdcode.wanted.ecommerce.modules.products.domain.entity.ProductId;
import lkdcode.wanted.ecommerce.modules.products.domain.entity.ProductOptionGroupId;
import lkdcode.wanted.ecommerce.modules.products.domain.value.option.ProductOptionDisplayOrderList;
import lkdcode.wanted.ecommerce.modules.products.domain.value.option.ProductOptionGroupDisplayOrderList;

public interface OptionValidator {
    void validOptionGroup(ProductOptionGroupDisplayOrderList list);

    void validOption(ProductOptionDisplayOrderList list);

    void validOptionGroup(ProductId productId, ProductOptionGroupId groupId);
}
