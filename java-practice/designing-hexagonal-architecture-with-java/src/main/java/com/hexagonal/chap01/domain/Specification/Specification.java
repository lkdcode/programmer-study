package com.hexagonal.chap01.domain.Specification;

public interface Specification<T> {
    boolean isSatisfiedBy(T t);

    Specification<T> and(Specification<T> specification);
}
