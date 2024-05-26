package org.game.domains.users.domain.value;

import org.game.domains.users.domain.specification.UserPasswordSpecification;

public record UserPassword(
        String password
) {
    public UserPassword(String password) {
        this.password = password;
        UserPasswordSpecification.spec(this);
    }
}
