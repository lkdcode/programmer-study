package lkdcode.wanted.ecommerce.modules.products.application.usecase.option.command;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record AddOptionResult(
    Long id,
    Long option_group_id,
    String name,
    BigDecimal additional_price,
    String sku,
    Integer stock,
    Integer display_order
) {
}
