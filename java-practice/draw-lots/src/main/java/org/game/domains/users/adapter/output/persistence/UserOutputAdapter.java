package org.game.domains.users.adapter.output.persistence;

import lombok.RequiredArgsConstructor;
import org.game.domains.users.adapter.output.persistence.mapper.UserEntityMapper;
import org.game.domains.users.application.ports.output.UserRepository;
import org.game.domains.users.domain.entity.User;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserOutputAdapter implements UserRepository {
    private final UserJPARepository userJPARepository;

    @Override
    public void save(User user) {
        userJPARepository.save(UserEntityMapper.convert(user));
    }
}
