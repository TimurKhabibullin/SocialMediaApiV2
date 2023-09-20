package ru.timur.socialmediaapi.SocialMediaApi.adapter.mapper;

import org.springframework.stereotype.Component;
import ru.timur.socialmediaapi.SocialMediaApi.core.model.PersonModel;
import ru.timur.socialmediaapi.SocialMediaApi.db.entity.Person;

@Component
public class PeopleMapper {
    public PersonModel mapToModel(Person personEntity){
        return new PersonModel(personEntity.getId(),personEntity.getUsername(),personEntity.getEmail(),personEntity.getPassword(),personEntity.getRole());
    }

    public Person mapToEntity(PersonModel personModel){
        return new Person(personModel.getId(),personModel.getUsername(),personModel.getEmail(),personModel.getPassword(),personModel.getRole());
    }
}
