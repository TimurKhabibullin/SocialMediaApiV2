package ru.timur.socialmediaapi.SocialMediaApi.core.model;

import lombok.Data;

@Data
public class PersonModel {
    private Integer id;
    private String username;
    private String email;
    private String password;
    private String role;

    public PersonModel(Integer id, String username, String email, String password, String role) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
    }
}