package lkdcode.wanted.ecommerce.modules.products.application.usecase.query;

import lkdcode.wanted.ecommerce.modules.products.application.ports.out.query.QueryProductOutPort;
import lkdcode.wanted.ecommerce.modules.products.application.usecase.query.dto.QueryProductResult;
import lkdcode.wanted.ecommerce.modules.products.application.usecase.query.value.QueryParamConditions;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QueryProductService {
    private final QueryProductOutPort productOutPort;

    public QueryProductResult getPreviewList(final Pageable pageable, final QueryParamConditions queryParamConditions) {
        return productOutPort.loadList(pageable, queryParamConditions);
    }
}