package com.level6;

class Twootr {

    FollowStatus onFollow(User user, String userIdToFollow) {
        return null;
    }

    DeleteStatus onDeleteTwoot(String id, String id1) {
        return null;
    }

    Position onSendTwoot(String id, User user, String content) {
        final String userId = user.getId();
        Twoot twoot = new Twoot(id, userId, content);

        user.followers()
                .filter(User::isLoggedOn)
                .forEach(follower -> follower.receiveTwoot(twoot));
        return null;
    }
}
