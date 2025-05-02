package lkdcode.wanted.ecommerce.modules.products.domain.model;

import lkdcode.wanted.ecommerce.modules.products.domain.entity.BrandId;
import lkdcode.wanted.ecommerce.modules.products.domain.entity.SellerId;

public record CreateProductModel(
    SellerId sellerId,
    BrandId brandId
) {
}
