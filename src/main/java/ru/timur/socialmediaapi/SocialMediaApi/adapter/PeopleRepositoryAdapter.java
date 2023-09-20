package ru.timur.socialmediaapi.SocialMediaApi.adapter;

import org.springframework.stereotype.Component;
import ru.timur.socialmediaapi.SocialMediaApi.core.model.PersonModel;
import ru.timur.socialmediaapi.SocialMediaApi.db.repository.PeopleRepository;
import ru.timur.socialmediaapi.SocialMediaApi.adapter.mapper.PeopleMapper;

@Component
public class PeopleRepositoryAdapter {
    private final PeopleRepository peopleRepository;
    private final PeopleMapper peopleMapper;

    public PeopleRepositoryAdapter(PeopleRepository peopleRepository, PeopleMapper peopleMapper) {
        this.peopleRepository = peopleRepository;
        this.peopleMapper = peopleMapper;
    }

    public PersonModel findByUsername(String username){
        return peopleMapper.mapToModel(peopleRepository.findByUsername(username).get());
    }

    public PersonModel findById(int id) {
        return peopleMapper.mapToModel(peopleRepository.findById(id).get());
    }

    public void save(PersonModel personModel) {
        peopleRepository.save(peopleMapper.mapToEntity(personModel));
    }

    public boolean existsByEmail(String email) {
        return peopleRepository.existsByEmail(email);
    }
}
