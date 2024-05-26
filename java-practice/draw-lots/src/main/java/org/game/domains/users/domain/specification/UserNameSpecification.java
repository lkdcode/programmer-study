package org.game.domains.users.domain.specification;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.game.domains.users.domain.value.UserName;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserNameSpecification {
    private static final String KOREAN_NAME_PATTERN = "^[가-힣]*$";

    public static void spec(final UserName userName) {
        if (!userName.name().matches(KOREAN_NAME_PATTERN)) {
            throw new IllegalArgumentException("Invalid name");
        }
    }
}