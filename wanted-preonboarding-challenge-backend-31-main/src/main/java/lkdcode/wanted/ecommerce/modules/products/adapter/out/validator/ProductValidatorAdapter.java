package lkdcode.wanted.ecommerce.modules.products.adapter.out.validator;

import lkdcode.wanted.ecommerce.framework.common.exception.ApplicationException;
import lkdcode.wanted.ecommerce.framework.common.exception.ApplicationResponseCode;
import lkdcode.wanted.ecommerce.modules.products.adapter.infrastructure.jpa.repository.query.QueryProductJpaRepository;
import lkdcode.wanted.ecommerce.modules.products.application.ports.out.validator.ProductValidator;
import lkdcode.wanted.ecommerce.modules.products.domain.value.ProductSlug;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductValidatorAdapter implements ProductValidator {
    private final QueryProductJpaRepository repository;

    @Override
    public void validUniqueSlug(ProductSlug target) {
        final var exists = repository.existsSlug(target.value());
        if (exists) {
            throw new ApplicationException(ApplicationResponseCode.DUPLICATE_PRODUCT_SLUG);
        }
    }
}
