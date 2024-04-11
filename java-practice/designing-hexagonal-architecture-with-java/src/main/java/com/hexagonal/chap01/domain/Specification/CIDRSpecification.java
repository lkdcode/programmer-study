package com.hexagonal.chap01.domain.Specification;

public class CIDRSpecification extends AbstractSpecification<Integer> {
    public static final int MINIMUM_ALLOWED_CIDR = 8;

    @Override
    public boolean isSatisfiedBy(Integer cidr) {
        return cidr > MINIMUM_ALLOWED_CIDR;
    }
}
