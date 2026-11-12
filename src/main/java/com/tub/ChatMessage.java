package com.tub;

public class ChatMessage {

    private final String id;
    private final String user;
    private final String text;
    private final String timestamp;

    public ChatMessage(String id,
                       String user,
                       String text,
                       String timestamp) {

        this.id = id;
        this.user = user;
        this.text = text;
        this.timestamp = timestamp;
    }

    public String getId() {
        return id;
    }

    public String getUser() {
        return user;
    }

    public String getText() {
        return text;
    }

    public String getTimestamp() {
        return timestamp;
    }
}
