package com.hexagonal.chap01.application.usecase;

import com.hexagonal.chap01.domain.entity.Router;

import java.util.List;
import java.util.function.Predicate;

public interface RouterViewUseCase {
    List<Router> getRouters(Predicate<Router> filter);
}
