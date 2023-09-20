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
@Table(name = "friendships")
public class Friendship {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "user1_id")
    private int user1;
    @Column(name = "user2_id")
    private int user2;

    public Friendship() {
    }

    public Friendship(int id, int user1, int user2) {
        this.id = id;
        this.user1 = user1;
        this.user2 = user2;
    }
}
