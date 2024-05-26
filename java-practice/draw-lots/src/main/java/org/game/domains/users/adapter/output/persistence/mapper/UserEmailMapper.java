package org.game.domains.users.adapter.output.persistence.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.game.domains.users.domain.value.UserEmail;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserEmailMapper {

    public static UserEmail convert(final String email) {
        return new UserEmail(email);
    }

    public static String convert(final UserEmail userEmail) {
        return userEmail.email();
    }
}
