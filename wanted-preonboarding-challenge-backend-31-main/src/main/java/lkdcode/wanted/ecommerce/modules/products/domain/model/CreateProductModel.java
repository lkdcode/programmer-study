package lkdcode.wanted.ecommerce.modules.products.domain.model;

import lkdcode.wanted.ecommerce.modules.products.domain.entity.ProductBrandId;
import lkdcode.wanted.ecommerce.modules.products.domain.entity.ProductSellerId;

public record CreateProductModel(
    ProductSellerId sellerId,
    ProductBrandId brandId
) {
}
