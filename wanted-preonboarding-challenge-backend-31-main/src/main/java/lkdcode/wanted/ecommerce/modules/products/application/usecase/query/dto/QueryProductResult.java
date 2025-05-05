package lkdcode.wanted.ecommerce.modules.products.application.usecase.query.dto;

import lkdcode.wanted.ecommerce.modules.products.application.usecase.query.value.Pagination;
import lombok.Builder;

import java.util.List;

@Builder
public record QueryProductResult(
    List<QueryProductDTO> items,
    Pagination pagination
) {
}