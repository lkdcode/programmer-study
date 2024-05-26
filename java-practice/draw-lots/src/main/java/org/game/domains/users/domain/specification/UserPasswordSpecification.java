package org.game.domains.users.domain.specification;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.game.domains.users.domain.value.UserPassword;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserPasswordSpecification {
    private static final String PASSWORD_PATTERN = "^(?=.*[a-z])(?=.*\\d)(?=.*[!@#$%^&*()]).{8,20}$";

    public static void spec(final UserPassword userPassword) {
        if (!userPassword.password().matches(PASSWORD_PATTERN)) {
            throw new IllegalArgumentException("Invalid password");
        }
    }
}