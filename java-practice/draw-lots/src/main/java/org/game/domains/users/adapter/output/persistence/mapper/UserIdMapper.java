package org.game.domains.users.adapter.output.persistence.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.game.domains.users.domain.entity.UserId;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserIdMapper {
    public static UserId convert(final Long userId) {
        return new UserId(userId);
    }

    public static Long convert(final UserId userId) {
        return userId.id();
    }
}
