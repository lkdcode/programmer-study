package com.hexagonal.chap01.Specification;

import com.hexagonal.chap01.domain.entity.Router;

public class NetworkAvailabilitySpecification extends AbstractSpecification<Router> {
    private IP address;
    private String name;
    private int cidr;

    public NetworkAvailabilitySpecification(IP address, String name, int cidr) {
        this.address = address;
        this.name = name;
        this.cidr = cidr;
    }

    @Override
    public boolean isSatisfiedBy(Router router) {
        return router != null && isNetworkAvailable(router);
    }

    private boolean isNetworkAvailable(Router router) {
        var availability = true;
        for (Network network : router.retrieveNetworks()) {
            if (network.getAddress().equals(address)
                    && network.getName().equals(name)
                    && network.getCidr() == cidr) {
                availability = false;
                break;
            }
        }

        return availability;
    }
}
