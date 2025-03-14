package org.game.domains.moku.application.ports.input.command;

import org.game.domains.moku.application.useCase.command.PlayerSetUpUseCase;
import org.game.domains.moku.domain.dsl.MokuConfigDSL;
import org.springframework.stereotype.Service;

@Service
public class PlayerSetUpPort implements PlayerSetUpUseCase {

    @Override
    public MokuConfigDSL setting() {
        MokuConfigDSL.init()
            .boardSize()
            .playerCount()
            .winCount();

        return null;
    }
}