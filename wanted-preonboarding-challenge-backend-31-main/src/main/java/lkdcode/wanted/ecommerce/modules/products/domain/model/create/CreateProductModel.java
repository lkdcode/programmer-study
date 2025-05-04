package lkdcode.wanted.ecommerce.modules.products.domain.model.create;

import lkdcode.wanted.ecommerce.modules.products.domain.value.tag.ProductTagList;

public record CreateProductModel(
    ProductValues productValues,

    ProductCategoryList categoryList,
    ProductDetailModel detailModel,
    ProductImageList imageModel,

    ProductPriceModel priceModel,
    ProductOptionGroupList optionGroupList,
    ProductTagList tagList
) {
}