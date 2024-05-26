package org.game.domains.users.adapter.output.persistence.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.game.domains.users.domain.value.UserName;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserNameMapper {
    public static UserName convert(final String name) {
        return new UserName(name);
    }

    public static String convert(final UserName userName) {
        return userName.name();
    }
}
