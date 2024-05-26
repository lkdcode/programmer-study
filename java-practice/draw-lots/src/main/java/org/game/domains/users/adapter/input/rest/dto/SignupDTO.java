package org.game.domains.users.adapter.input.rest.dto;

import org.game.domains.users.domain.entity.User;
import org.game.domains.users.domain.value.UserEmail;
import org.game.domains.users.domain.value.UserName;
import org.game.domains.users.domain.value.UserPassword;

public record SignupDTO(
        String username,
        String email,
        String password1,
        String password2
) {
    public User convert() {
        return User.builder()
                .userName(new UserName(username))
                .userPassword(new UserPassword(password1))
                .userEmail(new UserEmail(email))
                .build();
    }
}