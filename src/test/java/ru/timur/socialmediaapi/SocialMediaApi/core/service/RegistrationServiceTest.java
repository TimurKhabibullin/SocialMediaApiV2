package ru.timur.socialmediaapi.SocialMediaApi.core.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.timur.socialmediaapi.SocialMediaApi.adapter.PeopleRepositoryAdapter;
import ru.timur.socialmediaapi.SocialMediaApi.core.model.PersonModel;

@ContextConfiguration(classes = {RegistrationService.class})
@ExtendWith(SpringExtension.class)
class RegistrationServiceTest {
    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private PeopleRepositoryAdapter peopleRepositoryAdapter;

    @Autowired
    private RegistrationService registrationService;

    @Test
    void testRegister() {
        doNothing().when(peopleRepositoryAdapter).save(Mockito.<PersonModel>any());
        when(passwordEncoder.encode(Mockito.<CharSequence>any())).thenReturn("secret");
        PersonModel personModel = new PersonModel(1, "janedoe", "jane.doe@example.org", "iloveyou", "Role");

        registrationService.register(personModel);
        verify(peopleRepositoryAdapter).save(Mockito.<PersonModel>any());
        verify(passwordEncoder).encode(Mockito.<CharSequence>any());
        assertEquals("ROLE_USER", personModel.getRole());
        assertEquals("secret", personModel.getPassword());
    }
}

