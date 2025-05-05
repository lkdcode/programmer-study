package lkdcode.wanted.ecommerce.modules.products.adapter.out.validator;

import lkdcode.wanted.ecommerce.framework.common.exception.ApplicationException;
import lkdcode.wanted.ecommerce.framework.common.exception.ApplicationResponseCode;
import lkdcode.wanted.ecommerce.modules.products.adapter.infrastructure.jpa.repository.query.QueryProductOptionGroupJpaRepository;
import lkdcode.wanted.ecommerce.modules.products.application.ports.out.validator.OptionValidator;
import lkdcode.wanted.ecommerce.modules.products.domain.entity.ProductId;
import lkdcode.wanted.ecommerce.modules.products.domain.entity.ProductOptionGroupId;
import lkdcode.wanted.ecommerce.modules.products.domain.value.option.ProductOptionDisplayOrderList;
import lkdcode.wanted.ecommerce.modules.products.domain.value.option.ProductOptionGroupDisplayOrderList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
public class OptionValidatorAdapter implements OptionValidator {

    private final QueryProductOptionGroupJpaRepository optionGroupJpaRepository;

    @Override
    public void validOptionGroup(ProductOptionGroupDisplayOrderList list) {
        validIncrement(list);
        validDuplicate(list);
    }

    private static void validIncrement(final ProductOptionGroupDisplayOrderList list) {
        final var expected = new AtomicInteger(list.list().get(0).value());

        list.forEach(e -> {
            final var value = e.value();
            if (value != expected.getAndIncrement()) {
                throw new ApplicationException(ApplicationResponseCode.INVALID_OPTION_GROUP_DISPLAY_ORDER);
            }
        });
    }

    private static void validDuplicate(final ProductOptionGroupDisplayOrderList list) {
        final var set = new HashSet<>(list.list());
        if (set.size() != list.size()) {
            throw new ApplicationException(ApplicationResponseCode.DUPLICATE_OPTION_GROUP_DISPLAY_ORDER);
        }
    }

    @Override
    public void validOption(ProductOptionDisplayOrderList list) {
        validIncrement(list);
        validDuplicate(list);
    }

    @Override
    public void validOptionGroup(ProductId productId, ProductOptionGroupId groupId) {
        final var exists = optionGroupJpaRepository.existsId(productId.value(), groupId.value());

        if (!exists) {
            throw new ApplicationException(ApplicationResponseCode.NOT_FOUND_OPTION_GROUP);
        }
    }

    private static void validIncrement(final ProductOptionDisplayOrderList list) {
        final var expected = new AtomicInteger(list.list().get(0).value());

        list.forEach(e -> {
            final var value = e.value();
            if (value != expected.getAndIncrement()) {
                throw new ApplicationException(ApplicationResponseCode.INVALID_OPTION_DISPLAY_ORDER);
            }
        });
    }

    private static void validDuplicate(final ProductOptionDisplayOrderList list) {
        final var set = new HashSet<>(list.list());
        if (set.size() != list.size()) {
            throw new ApplicationException(ApplicationResponseCode.DUPLICATE_OPTION_DISPLAY_ORDER);
        }
    }
}
