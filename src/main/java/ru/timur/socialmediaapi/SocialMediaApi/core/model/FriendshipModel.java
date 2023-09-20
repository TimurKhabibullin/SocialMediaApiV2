package ru.timur.socialmediaapi.SocialMediaApi.core.model;

import lombok.Data;

@Data
public class FriendshipModel {
    private int id;
    private int user1;
    private int user2;

    public FriendshipModel(int sender, int recipient) {
    }

    public FriendshipModel() {
    }

    public FriendshipModel(int id, int user1, int user2) {
        this.id = id;
        this.user1 = user1;
        this.user2 = user2;
    }
}
