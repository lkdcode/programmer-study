package org.game.domains.users.adapter.output.persistence;

import org.game.domains.users.adapter.output.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJPARepository extends JpaRepository<UserEntity, Long> {
}
