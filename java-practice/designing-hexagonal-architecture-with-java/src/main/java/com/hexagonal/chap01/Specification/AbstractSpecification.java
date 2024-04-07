package com.hexagonal.chap01.Specification;

public abstract class AbstractSpecification<T> implements Specification<T> {
    public abstract boolean isSatisfiedBy(T t);

    @Override
    public Specification<T> and(Specification<T> specification) {
        return new AndSpecification<>(this, specification);
    }
}
