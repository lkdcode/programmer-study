package lkdcode.wanted.ecommerce.modules.products.application.usecase.command.create;

import jakarta.transaction.Transactional;
import lkdcode.wanted.ecommerce.modules.products.application.ports.out.command.CommandProductOutPort;
import lkdcode.wanted.ecommerce.modules.products.application.ports.out.validator.*;
import lkdcode.wanted.ecommerce.modules.products.domain.model.create.CreateProductModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class CreateProductService {
    private final CommandProductOutPort commandProductOutPort;

    private final ProductValidator productValidator;
    private final BrandValidator brandIdValidator;
    private final SellerValidator sellerIdValidator;
    private final CategoryValidator categoryValidator;
    private final OptionValidator optionValidator;
    private final ImageValidator imageValidator;
    private final TagValidator tagValidator;

    public SaveResult create(final CreateProductModel model) {
        return CreateProductUsecase.execute(model)
            .validBrandId(brandIdValidator::validId)
            .validSellerId(sellerIdValidator::validId)
            .validUniqueSlug(productValidator::validUniqueSlug)
            .saveProduct(commandProductOutPort::save)

            .validProductCategoryList(categoryValidator::validList)
            .saveProductCategory(commandProductOutPort::saveCategory)

            .saveProductDetail(commandProductOutPort::saveDetail)

            .validProductOptionGroup(optionValidator::validOptionGroup)
            .validProductOption(optionValidator::validOption)
            .saveProductOption(commandProductOutPort::saveOption)

            .validImageDisplayOrder(imageValidator::validImageDisplayOrder)
            .saveImage(commandProductOutPort::saveImage)

            .saveProductPrice(commandProductOutPort::savePrice)

            .validProductTag(tagValidator::validList)
            .saveProductTag(commandProductOutPort::saveTag)

            .done();
    }
}