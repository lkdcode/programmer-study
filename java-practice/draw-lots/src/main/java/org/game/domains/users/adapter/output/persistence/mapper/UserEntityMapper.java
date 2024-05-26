package org.game.domains.users.adapter.output.persistence.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.game.domains.users.adapter.output.persistence.entity.UserEntity;
import org.game.domains.users.domain.entity.User;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserEntityMapper {
    public static UserEntity convert(final User user) {
        final var userEmail = user.getUserEmail();
        final var userName = user.getUserName();
        final var userPassword = user.getUserPassword();

        return UserEntity.builder()
                .username(UserNameMapper.convert(userName))
                .userEmail(UserEmailMapper.convert(userEmail))
                .userPassword(UserPasswordMapper.convert(userPassword))
                .build();
    }

    public static User convert(final UserEntity userEntity) {
        final var userEmail = userEntity.getUserEmail();
        final var userPassword = userEntity.getUserPassword();
        final var username = userEntity.getUsername();
        final var userId = userEntity.getId();

        return User.builder()
                .userId(UserIdMapper.convert(userId))
                .userName(UserNameMapper.convert(username))
                .userEmail(UserEmailMapper.convert(userEmail))
                .userPassword(UserPasswordMapper.convert(userPassword))
                .build();
    }
}
