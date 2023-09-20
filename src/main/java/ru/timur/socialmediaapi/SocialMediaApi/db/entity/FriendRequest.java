package ru.timur.socialmediaapi.SocialMediaApi.db.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "friend_requests")
public class FriendRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "sender_id")
    private int sender;
    @Column(name = "recipient_id")
    private int recipient;
    @Column(name = "status")
    private String status;

    public FriendRequest() {
    }

    public FriendRequest(int id, int sender, int recipient, String status) {
        this.id = id;
        this.sender = sender;
        this.recipient = recipient;
        this.status = status;
    }
}