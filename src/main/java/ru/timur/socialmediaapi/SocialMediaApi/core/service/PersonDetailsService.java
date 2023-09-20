package ru.timur.socialmediaapi.SocialMediaApi.core.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.timur.socialmediaapi.SocialMediaApi.adapter.PeopleRepositoryAdapter;
import ru.timur.socialmediaapi.SocialMediaApi.core.model.PersonModel;
import ru.timur.socialmediaapi.SocialMediaApi.config.security.PersonDetails;

import java.util.Optional;

@Service
public class PersonDetailsService implements UserDetailsService {
    private final PeopleRepositoryAdapter peopleRepositoryAdapter;

    public PersonDetailsService(PeopleRepositoryAdapter peopleRepositoryAdapter) {
        this.peopleRepositoryAdapter = peopleRepositoryAdapter;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<PersonModel> person = Optional.ofNullable(peopleRepositoryAdapter.findByUsername(username));
        if (person.isEmpty())
            throw new UsernameNotFoundException("User not found");

        return new PersonDetails(person.get());
    }

    public PersonModel findByUsername(String username){
        return peopleRepositoryAdapter.findByUsername(username);
    }

    public PersonModel findByPersonId(int id){
        return peopleRepositoryAdapter.findById(id);
    }

    @Transactional
    public void edit(PersonModel personModel){
        peopleRepositoryAdapter.save(personModel);
    }
}
