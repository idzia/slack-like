package com.codecool;


import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.regex.Pattern;

public class Message implements Serializable {
    private String content;
    private String author;
    private String channel;
    private LocalDateTime createdAt ;
    private final String JOIN = "^\\/join #\\w+$";
    private final String LEAVE = "^\\/leave #\\w+$";
    private final String WELCOME = "*welcome*";
    private final int CHANNEL_INDEX = 1;

    public Message(String content, String author, String channel) {
        this.content = content;
        this.author = author;
        this.channel = channel;
        this.createdAt = LocalDateTime.now();
    }

//    public String getContent() {
//        return content;
//    }

    public boolean isJoinMessage() {
        return Pattern.matches(JOIN, content);
    }

    public boolean isLeaveMessage() {
        return Pattern.matches(LEAVE, content);
    }

    public boolean isWelcomeMessage() {
        return content.equals(WELCOME);
    }

    public String getChannelFromContent() {
        return content.split("\\s+")[CHANNEL_INDEX];
    }

    public String getAuthor() {
        return author;
    }

    public String getChannel() {
        return channel;
    }

    @Override
    public String toString() {
        StringBuilder message = new StringBuilder();

        message.append(createdAt.getHour());
        message.append(":");
        message.append(createdAt.getMinute());
        message.append(":");
        message.append(createdAt.getSecond());
        message.append("-");
        message.append(author);
        message.append(": ");
        message.append(content);

        return message.toString();
    }
}
