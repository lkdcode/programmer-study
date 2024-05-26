package org.game.domains.users.adapter.output.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@Table(name = "tb_users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String userEmail;
    private String userPassword;

    @Builder
    public UserEntity(String username, String userEmail, String userPassword) {
        this.username = username;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
    }
}
