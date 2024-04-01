package com.hexagonal.chap01.application.port.output;

import com.hexagonal.chap01.domain.entity.Router;

import java.util.List;

public interface RouterViewOutputPort {
    List<Router> fetchRouters();
}
