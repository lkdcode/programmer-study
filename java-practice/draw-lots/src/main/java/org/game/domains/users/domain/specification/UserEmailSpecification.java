package org.game.domains.users.domain.specification;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.game.domains.users.domain.value.UserEmail;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserEmailSpecification {
    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9+-\\_.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$";

    public static void spec(final UserEmail userEmail) {
        if (!userEmail.email().matches(EMAIL_PATTERN)) {
            throw new IllegalArgumentException("Invalid email");
        }
    }
}