package com.hexagonal.chap01.domain.entity;

import com.hexagonal.chap01.domain.Specification.IP;
import com.hexagonal.chap01.domain.Specification.Network;
import com.hexagonal.chap01.domain.value.RouterId;
import com.hexagonal.chap01.domain.value.RouterType;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Router {
    private final RouterType routerType;
    private final RouterId routerId;

    public Router(RouterType routerType, RouterId routerId) {
        this.routerType = routerType;
        this.routerId = routerId;
    }

    public static Predicate<Router> filterRouterByType(RouterType routerType) {
        return routerType.equals(RouterType.CORE)
                ? isCore() :
                isEdge();
    }

    private static Predicate<Router> isCore() {
        return p -> p.getRouterType() == RouterType.CORE;
    }

    private static Predicate<Router> isEdge() {
        return p -> p.getRouterType() == RouterType.EDGE;
    }

    public static List<Router> retrieveRouter(List<Router> routers, Predicate<Router> predicate) {
        return routers.stream()
                .filter(predicate)
                .collect(Collectors.<Router>toList());
    }

    public RouterType getRouterType() {
        return routerType;
    }

    public List<Network> retrieveNetworks() {
        return null;
    }

    public Network createNetwork(IP address, String name, int cidr) {
        return null;
    }

    public void addNetworkToSwitch(Network network) {

    }
}