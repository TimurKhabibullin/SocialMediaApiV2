package ru.timur.socialmediaapi.SocialMediaApi.core.model;

import lombok.Data;

@Data
public class FriendRequestModel {
    private int id;
    private int sender;
    private int recipient;
    private String status;

    public FriendRequestModel() {
    }

    public FriendRequestModel(int id, int sender, int recipient, String status) {
        this.id = id;
        this.sender = sender;
        this.recipient = recipient;
        this.status = status;
    }
}