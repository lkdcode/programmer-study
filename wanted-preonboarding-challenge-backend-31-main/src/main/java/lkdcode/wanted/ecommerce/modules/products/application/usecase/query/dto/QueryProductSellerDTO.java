package lkdcode.wanted.ecommerce.modules.products.application.usecase.query.dto;

import lombok.Builder;

@Builder
public record QueryProductSellerDTO(
    Long id,
    String name
) {
}
