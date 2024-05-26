package org.game.domains.users.domain.value;

import org.game.domains.users.domain.specification.UserNameSpecification;

public record UserName(
        String name
) {
    public UserName(String name) {
        this.name = name;
        UserNameSpecification.spec(this);
    }
}
