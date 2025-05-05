package lkdcode.wanted.ecommerce.modules.products.application.ports.out.query;

import lkdcode.wanted.ecommerce.modules.products.application.usecase.query.dto.QueryProductResult;
import lkdcode.wanted.ecommerce.modules.products.application.usecase.query.value.QueryParamConditions;
import org.springframework.data.domain.Pageable;

public interface QueryProductOutPort {
    QueryProductResult loadList(Pageable pageable, QueryParamConditions queryParamConditions);
}
