package lkdcode.wanted.ecommerce.modules.products.adapter.in.rest.command.mapper;

import lkdcode.wanted.ecommerce.modules.products.adapter.in.rest.command.dto.request.create.UpsertProductDTO;
import lkdcode.wanted.ecommerce.modules.products.adapter.in.rest.command.dto.response.CreateResponse;
import lkdcode.wanted.ecommerce.modules.products.application.usecase.command.UpsertResult;
import lkdcode.wanted.ecommerce.modules.products.domain.model.create.CreateProductModel;
import lkdcode.wanted.ecommerce.modules.products.domain.model.update.UpdateProductModel;
import org.springframework.stereotype.Service;

@Service
public class CommandProductApiMapper {

    public CreateProductModel convertCreateModel(final UpsertProductDTO dto) {
        return new CreateProductModel(
            dto.getProductValues(),
            dto.getProductCategoryList(),
            dto.getProductDetailModel(),
            dto.getProductImageList(),
            dto.getProductPriceModel(),
            dto.getProductOptionGroupList(),
            dto.getProductTagList()
        );
    }

    public UpdateProductModel convertUpdateModel(final UpsertProductDTO dto) {
        return new UpdateProductModel(
            dto.getProductValues(),
            dto.getProductCategoryList(),
            dto.getProductDetailModel(),
            dto.getProductImageList(),
            dto.getProductPriceModel(),
            dto.getProductOptionGroupList(),
            dto.getProductTagList()
        );
    }

    public CreateResponse convert(final UpsertResult result) {
        return CreateResponse.builder()
            .id(result.id().value())
            .name(result.name().value())
            .slug(result.slug().value())
            .createdAt(result.createdAt())
            .updatedAt(result.updatedAt())
            .build();
    }
}