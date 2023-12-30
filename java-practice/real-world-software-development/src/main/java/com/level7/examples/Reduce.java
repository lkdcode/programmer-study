package com.level7.examples;


import com.level6.Position;
import com.level6.Twoot;

import java.util.Comparator;
import java.util.List;
import java.util.function.BinaryOperator;

class Reduce {
    private final BinaryOperator<Position> maxPosition = BinaryOperator.maxBy(Comparator.comparingInt(Position::getValue));

    Twoot combineTwootsBy(
            final List<Twoot> twoots
            , final String senderId
            , final String newId
    ) {
        return twoots
                .stream()
                .reduce(
                        new Twoot(newId, senderId, "", Position.INITIAL_POSITION),
                        (acc, twoot) -> new Twoot(
                                newId,
                                senderId,
                                twoot.getContent() + acc.getContent(),
                                maxPosition.apply(acc.getPosition(), twoot.getPosition())
                        )
                );
    }
}
