package org.game.domains.users.application.usecases;

import org.game.domains.users.domain.entity.User;

public interface SignUseCase {
    void signup(User user);

    void signin(User user);
}
