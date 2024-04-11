package com.hexagonal.chap01.domain.Specification;

import com.hexagonal.chap01.domain.entity.Router;

public class NetworkAmountSpecification extends AbstractSpecification<Router> {
    private static final int MAXIMUM_ALLOWED_NETWORKS = 6;

    @Override
    public boolean isSatisfiedBy(Router router) {
        return router.retrieveNetworks().size() <= MAXIMUM_ALLOWED_NETWORKS;
    }
}
