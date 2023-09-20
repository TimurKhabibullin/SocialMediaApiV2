package ru.timur.socialmediaapi.SocialMediaApi.core.utils;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;
import ru.timur.socialmediaapi.SocialMediaApi.adapter.PeopleRepositoryAdapter;

@Component
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    private final PeopleRepositoryAdapter peopleRepositoryAdapter;

    public UniqueEmailValidator(PeopleRepositoryAdapter peopleRepositoryAdapter) {
        this.peopleRepositoryAdapter = peopleRepositoryAdapter;
    }


    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        return email != null && !peopleRepositoryAdapter.existsByEmail(email);
    }
}
