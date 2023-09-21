package ru.timur.socialmediaapi.SocialMediaApi.db.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "post")
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "header")
    private String header;
    @Column(name = "text")
    private String text;
    @Column(name = "link_for_image")
    private String linkForImage;
    @Column(name = "date_of_create")
    private long dateOfCreate;
    @Column(name = "person_id")
    private int person;
}
