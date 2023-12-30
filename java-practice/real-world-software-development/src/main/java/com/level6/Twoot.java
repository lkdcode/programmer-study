package com.level6;

public class Twoot {
    private final String id;
    private final String senderId;
    private final String content;
    private final Position position;

    public Twoot(String id, String senderId, String content, Position position) {
        this.id = id;
        this.senderId = senderId;
        this.content = content;
        this.position = position;
    }

    public boolean isAfter(final Position otherPosition) {
        return position.getValue() > otherPosition.getValue();
    }

    public String getId() {
        return id;
    }

    public String getSenderId() {
        return senderId;
    }

    public String getContent() {
        return content;
    }

    public Position getPosition() {
        return position;
    }
}
