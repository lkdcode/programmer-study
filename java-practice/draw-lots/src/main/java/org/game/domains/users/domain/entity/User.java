package org.game.domains.users.domain.entity;

import lombok.Builder;
import lombok.Getter;
import org.game.domains.users.domain.value.UserEmail;
import org.game.domains.users.domain.value.UserName;
import org.game.domains.users.domain.value.UserPassword;

@Getter
public class User {
    private final UserId userId;
    private UserEmail userEmail;
    private UserName userName;
    private UserPassword userPassword;

    @Builder
    public User(UserId userId, UserEmail userEmail, UserName userName, UserPassword userPassword) {
        this.userId = userId;
        this.userEmail = userEmail;
        this.userName = userName;
        this.userPassword = userPassword;
    }
}