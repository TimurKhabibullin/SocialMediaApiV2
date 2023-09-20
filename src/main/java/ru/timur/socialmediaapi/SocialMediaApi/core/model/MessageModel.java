package ru.timur.socialmediaapi.SocialMediaApi.core.model;

import lombok.Data;

@Data
public class MessageModel {
    private int id;
    private int sender;
    private int recipient;
    private String text;

    public MessageModel(int id, int sender, int recipient, String text) {
        this.id = id;
        this.sender = sender;
        this.recipient = recipient;
        this.text = text;
    }
}