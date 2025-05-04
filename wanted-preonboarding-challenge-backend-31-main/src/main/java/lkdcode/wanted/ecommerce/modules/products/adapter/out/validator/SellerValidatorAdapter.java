package lkdcode.wanted.ecommerce.modules.products.adapter.out.validator;

import lkdcode.wanted.ecommerce.framework.common.exception.ApplicationException;
import lkdcode.wanted.ecommerce.framework.common.exception.ApplicationResponseCode;
import lkdcode.wanted.ecommerce.modules.products.adapter.external.seller.QuerySellerAdapter;
import lkdcode.wanted.ecommerce.modules.products.application.ports.out.validator.SellerValidator;
import lkdcode.wanted.ecommerce.modules.products.domain.entity.ProductSellerId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SellerValidatorAdapter implements SellerValidator {
    private final QuerySellerAdapter adapter;

    @Override
    public void validId(ProductSellerId target) {
        if (adapter.load(target.value()) == null) {
            throw new ApplicationException(ApplicationResponseCode.FAIL);
        }
    }
}
