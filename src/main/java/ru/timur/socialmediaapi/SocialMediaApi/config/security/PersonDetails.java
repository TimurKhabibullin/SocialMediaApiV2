package ru.timur.socialmediaapi.SocialMediaApi.config.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.timur.socialmediaapi.SocialMediaApi.core.model.PersonModel;

import java.util.Collection;
import java.util.Collections;

public class PersonDetails implements UserDetails {

    private final PersonModel personModel;

    public PersonDetails(PersonModel personModel) {
        this.personModel = personModel;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(personModel.getRole()));
    }

    @Override
    public String getPassword() {
        return this.personModel.getPassword();
    }

    @Override
    public String getUsername() {
        return this.personModel.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public PersonModel getPerson(){
        return this.personModel;
    }
}
