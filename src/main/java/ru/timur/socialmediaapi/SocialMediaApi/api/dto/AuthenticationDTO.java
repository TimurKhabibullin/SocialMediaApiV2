package ru.timur.socialmediaapi.SocialMediaApi.api.dto;

import lombok.Data;

@Data
public class AuthenticationDTO {
    private String username;

    private String password;
}
