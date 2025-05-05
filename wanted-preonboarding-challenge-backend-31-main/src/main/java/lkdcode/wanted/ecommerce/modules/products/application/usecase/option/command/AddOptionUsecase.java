package lkdcode.wanted.ecommerce.modules.products.application.usecase.option.command;

import lkdcode.wanted.ecommerce.modules.products.domain.entity.ProductId;
import lkdcode.wanted.ecommerce.modules.products.domain.entity.ProductOptionGroupId;
import lkdcode.wanted.ecommerce.modules.products.domain.model.option.AddOptionModel;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

class AddOptionUsecase {
    private final ProductId productId;
    private final AddOptionModel model;

    private AddOptionResult result;

    private AddOptionUsecase(ProductId productId, AddOptionModel model) {
        this.productId = productId;
        this.model = model;
    }

    public static AddOptionUsecase execute(final ProductId productId, final AddOptionModel model) {
        return new AddOptionUsecase(productId, model);
    }

    public AddOptionUsecase validOptionGroupId(final BiConsumer<ProductId, ProductOptionGroupId> valid) {
        valid.accept(productId, model.optionGroupId());
        return this;
    }

    public AddOptionUsecase addOption(final Function<AddOptionModel, AddOptionResult> add) {
        this.result = add.apply(model);
        return this;
    }

    public AddOptionResult done() {
        return result;
    }
}