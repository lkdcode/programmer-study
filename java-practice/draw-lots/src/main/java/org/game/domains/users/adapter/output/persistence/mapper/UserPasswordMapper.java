package org.game.domains.users.adapter.output.persistence.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.game.domains.users.domain.value.UserPassword;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserPasswordMapper {
    public static UserPassword convert(final String password) {
        return new UserPassword(password);
    }

    public static String convert(final UserPassword userPassword) {
        return userPassword.password();
    }
}
