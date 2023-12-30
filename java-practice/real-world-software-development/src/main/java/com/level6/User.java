package com.level6;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

public class User {
    private final String id;
    private final byte[] password;
    private final byte[] salt;
    private final Set<User> followers = new HashSet<>();
    private final Set<String> following = new HashSet<>();

    private Position lastSeenPosition;
    private ReceiverEndPoint receiverEndPoint;

    public User(
            final String id,
            final byte[] password,
            final byte[] salt,
            final Position lastSeenPosition) {

        Objects.requireNonNull(id, "id");
        Objects.requireNonNull(password, "password");

        this.id = id;
        this.password = password;
        this.salt = salt;
        this.lastSeenPosition = lastSeenPosition;
    }

    public String getId() {
        return id;
    }

    public byte[] getPassword() {
        return password;
    }

    public byte[] getSalt() {
        return salt;
    }

    boolean receiveTwoot(final Twoot twoot) {
        if (isLoggedOn()) {
            receiverEndPoint.onTwoot(twoot);
            return true;
        }

        return false;
    }

    boolean isLoggedOn() {
        return receiverEndPoint != null;
    }

    public FollowStatus addFollowers(final User user) {
        if (followers.add(user)) {
            user.following.add(id);
            return FollowStatus.SUCCESS;
        }

        return FollowStatus.ALREADY_FOLLOWING;
    }

    void onLogon(final ReceiverEndPoint receiverEndPoint) {
        this.receiverEndPoint = receiverEndPoint;
    }

    void onLogoff() {
        this.receiverEndPoint = null;
    }

    Stream<User> followers() {
        return followers.stream();
    }

    Set<String> getFollowing() {
        return following;
    }

    Position getLastSeenPosition() {
        return lastSeenPosition;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                '}';
    }

}
