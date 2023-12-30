package com.level7.in_memory;

import com.level6.FollowStatus;
import com.level6.User;
import com.level6.UserRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

class InMemoryUserRepository implements UserRepository {
    private final Map<String, User> userIdToUser = new HashMap<>();

    @Override
    public boolean add(User user) {
        return this.userIdToUser.putIfAbsent(user.getId(), user) == null;
    }

    @Override
    public Optional<User> get(String userId) {
        return Optional.ofNullable(this.userIdToUser.get(userId));
    }

    @Override
    public void update(User user) {
        this.userIdToUser.putIfAbsent(user.getId(), user);
    }

    @Override
    public void clear() {
        this.userIdToUser.clear();
    }

    @Override
    public FollowStatus follow(User follower, User userToFollow) {
        return userToFollow.addFollowers(follower);
    }

    @Override
    public void close() throws Exception {
    }
}
