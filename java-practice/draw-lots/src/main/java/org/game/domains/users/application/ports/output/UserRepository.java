package org.game.domains.users.application.ports.output;

import org.game.domains.users.domain.entity.User;

public interface UserRepository {
    void save(User user);
}
