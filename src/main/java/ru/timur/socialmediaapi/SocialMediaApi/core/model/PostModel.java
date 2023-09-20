package ru.timur.socialmediaapi.SocialMediaApi.core.model;

import lombok.Data;

@Data
public class PostModel {
    private int id;
    private String header;
    private String text;
    private String linkForImage;
    private long dateOfCreate;
    private int person;

    public PostModel(int id, String header, String text, String linkForImage, long dateOfCreate, int person) {
        this.id = id;
        this.header = header;
        this.text = text;
        this.linkForImage = linkForImage;
        this.dateOfCreate = dateOfCreate;
        this.person = person;
    }
}
