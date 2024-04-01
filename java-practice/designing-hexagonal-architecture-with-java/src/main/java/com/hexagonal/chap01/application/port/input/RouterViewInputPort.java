package com.hexagonal.chap01.application.port.input;

import com.hexagonal.chap01.application.port.output.RouterViewOutputPort;
import com.hexagonal.chap01.application.usecase.RouterViewUseCase;
import com.hexagonal.chap01.domain.entity.Router;

import java.util.List;
import java.util.function.Predicate;

public class RouterViewInputPort implements RouterViewUseCase {
    private final RouterViewOutputPort routerListOutputPort;

    public RouterViewInputPort(RouterViewOutputPort routerListOutputPort) {
        this.routerListOutputPort = routerListOutputPort;
    }

    @Override
    public List<Router> getRouters(Predicate<Router> filter) {
        var routers = routerListOutputPort.fetchRouters();
        return Router.retrieveRouter(routers, filter);
    }
}