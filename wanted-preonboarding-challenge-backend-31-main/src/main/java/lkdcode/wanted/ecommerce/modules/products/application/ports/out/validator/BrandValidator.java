package lkdcode.wanted.ecommerce.modules.products.application.ports.out.validator;

import lkdcode.wanted.ecommerce.modules.products.domain.entity.ProductBrandId;

public interface BrandValidator {
    void validId(final ProductBrandId target);
}
