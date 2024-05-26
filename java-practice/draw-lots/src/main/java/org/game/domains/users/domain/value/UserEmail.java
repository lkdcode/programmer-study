package org.game.domains.users.domain.value;

import org.game.domains.users.domain.specification.UserEmailSpecification;

public record UserEmail(
        String email
) {
    public UserEmail(String email) {
        this.email = email;
        UserEmailSpecification.spec(this);
    }
}