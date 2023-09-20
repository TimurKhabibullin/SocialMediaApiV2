package ru.timur.socialmediaapi.SocialMediaApi.core.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.timur.socialmediaapi.SocialMediaApi.adapter.PeopleRepositoryAdapter;
import ru.timur.socialmediaapi.SocialMediaApi.core.model.PersonModel;

@Service
public class RegistrationService {
    private final PeopleRepositoryAdapter peopleRepositoryAdapter;
    private final PasswordEncoder passwordEncoder;

    public RegistrationService(PeopleRepositoryAdapter peopleRepositoryAdapter, PasswordEncoder passwordEncoder) {
        this.peopleRepositoryAdapter = peopleRepositoryAdapter;
        this.passwordEncoder = passwordEncoder;
    }


    @Transactional
    public void register(PersonModel personModel){
        personModel.setRole("ROLE_USER");
        personModel.setPassword(passwordEncoder.encode(personModel.getPassword()));
        peopleRepositoryAdapter.save(personModel);
    }
}
