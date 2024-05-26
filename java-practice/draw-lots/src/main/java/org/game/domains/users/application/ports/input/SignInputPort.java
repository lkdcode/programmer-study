package org.game.domains.users.application.ports.input;

import lombok.RequiredArgsConstructor;
import org.game.domains.users.application.ports.output.UserRepository;
import org.game.domains.users.application.usecases.SignUseCase;
import org.game.domains.users.domain.entity.User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignInputPort implements SignUseCase {
    private final UserRepository userRepository;

    @Override
    public void signup(User user) {
        userRepository.save(user);
    }

    @Override
    public void signin(User user) {
    }
}
