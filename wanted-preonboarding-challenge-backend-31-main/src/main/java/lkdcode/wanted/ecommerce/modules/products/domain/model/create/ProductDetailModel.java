package lkdcode.wanted.ecommerce.modules.products.domain.model.create;

import lkdcode.wanted.ecommerce.modules.products.domain.value.detail.*;

public record ProductDetailModel(
    ProductWeight weight,
    ProductDimension dimension,
    ProductMaterial material,
    ProductCountryOfOrigin countryOfOrigin,
    ProductWarrantyInfo warrantyInfo,
    ProductCareInstructions careInstructions,
    ProductAdditionalInfo additionalInfo
) {
}
