package ru.timur.socialmediaapi.SocialMediaApi.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.timur.socialmediaapi.SocialMediaApi.db.entity.Person;
import java.util.Optional;

@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer> {
    Optional<Person> findByUsername(String userName);

    boolean existsByEmail(String email);
}
