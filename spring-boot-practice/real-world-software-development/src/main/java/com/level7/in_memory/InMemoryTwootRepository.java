package com.level7.in_memory;

import com.level6.Position;
import com.level6.Twoot;
import com.level6.TwootQuery;
import com.level6.TwootRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

class InMemoryTwootRepository implements TwootRepository {
    private final List<Twoot> twoots = new ArrayList<>();
    private Position currentPosition = Position.INITIAL_POSITION;

    @Override
    public Twoot add(String id, String userId, String content) {
        this.currentPosition = currentPosition.next();

        var twootPosition = this.currentPosition;
        var twoot = new Twoot(id, userId, content, twootPosition);
        this.twoots.add(twoot);

        return twoot;
    }

    @Override
    public Optional<Twoot> get(String id) {
        return Optional.empty();
    }

    @Override
    public void delete(Twoot twoot) {
        this.twoots.remove(twoot);
    }

    @Override
    public void query(TwootQuery twootQuery, Consumer<Twoot> callback) {
        if (!twootQuery.hasUsers()) {
            return;
        }

        var lastSeenPosition = twootQuery.getLastSeenPosition();
        var inUsers = twootQuery.getInUsers();

        twoots.stream()
                .filter(twoot -> inUsers.contains(twoot.getSenderId()))
                .filter(twoot -> twoot.isAfter(lastSeenPosition))
                .forEach(callback);
    }

    public void queryLoop(TwootQuery twootQuery, Consumer<Twoot> callback) {
        if (!twootQuery.hasUsers()) {
            return;
        }

        var lastSeenPosition = twootQuery.getLastSeenPosition();
        var inUsers = twootQuery.getInUsers();

        for (Twoot t : twoots) {
            if (inUsers.contains(t.getSenderId())
                    && t.isAfter(lastSeenPosition)) {
                callback.accept(t);
            }
        }
    }

    @Override
    public void clear() {
        this.twoots.clear();
    }
}
