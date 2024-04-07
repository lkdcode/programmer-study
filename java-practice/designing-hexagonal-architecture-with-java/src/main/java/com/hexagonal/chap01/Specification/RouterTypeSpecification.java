package com.hexagonal.chap01.Specification;

import com.hexagonal.chap01.domain.entity.Router;
import com.hexagonal.chap01.domain.value.RouterType;

public class RouterTypeSpecification extends AbstractSpecification<Router> {
    @Override
    public boolean isSatisfiedBy(Router router) {
        return router.getRouterType().equals(RouterType.EDGE) ||
                router.getRouterType().equals(RouterType.CORE);
    }
}
