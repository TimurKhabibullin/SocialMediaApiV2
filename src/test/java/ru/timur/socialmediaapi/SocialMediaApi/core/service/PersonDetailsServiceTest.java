package ru.timur.socialmediaapi.SocialMediaApi.core.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.timur.socialmediaapi.SocialMediaApi.adapter.PeopleRepositoryAdapter;
import ru.timur.socialmediaapi.SocialMediaApi.core.model.PersonModel;

@ContextConfiguration(classes = {PersonDetailsService.class})
@ExtendWith(SpringExtension.class)
class PersonDetailsServiceTest {
    @MockBean
    private FriendRequestService friendRequestService;

    @MockBean
    private FriendshipService friendshipService;

    @MockBean
    private PeopleRepositoryAdapter peopleRepositoryAdapter;

    @Autowired
    private PersonDetailsService personDetailsService;

    @Test
    void testLoadUserByUsername() throws UsernameNotFoundException {
        when(peopleRepositoryAdapter.findByUsername(Mockito.<String>any()))
                .thenReturn(new PersonModel(1, "janedoe", "jane.doe@example.org", "iloveyou", "Role"));
        assertEquals("iloveyou", personDetailsService.loadUserByUsername("janedoe").getPassword());
        verify(peopleRepositoryAdapter).findByUsername(Mockito.<String>any());
    }

    @Test
    void testLoadUserByUsername2() throws UsernameNotFoundException {
        when(peopleRepositoryAdapter.findByUsername(Mockito.<String>any())).thenReturn(null);
        assertThrows(UsernameNotFoundException.class, () -> personDetailsService.loadUserByUsername("janedoe"));
        verify(peopleRepositoryAdapter).findByUsername(Mockito.<String>any());
    }

    @Test
    void testLoadUserByUsername3() throws UsernameNotFoundException {
        when(peopleRepositoryAdapter.findByUsername(Mockito.<String>any()))
                .thenThrow(new UsernameNotFoundException("User not found"));
        assertThrows(UsernameNotFoundException.class, () -> personDetailsService.loadUserByUsername("janedoe"));
        verify(peopleRepositoryAdapter).findByUsername(Mockito.<String>any());
    }

    @Test
    void testFindByUsername() {
        PersonModel personModel = new PersonModel(1, "janedoe", "jane.doe@example.org", "iloveyou", "Role");

        when(peopleRepositoryAdapter.findByUsername(Mockito.<String>any())).thenReturn(personModel);
        assertSame(personModel, personDetailsService.findByUsername("janedoe"));
        verify(peopleRepositoryAdapter).findByUsername(Mockito.<String>any());
    }

    @Test
    void testFindByUsername2() {
        when(peopleRepositoryAdapter.findByUsername(Mockito.<String>any()))
                .thenThrow(new UsernameNotFoundException("Msg"));
        assertThrows(UsernameNotFoundException.class, () -> personDetailsService.findByUsername("janedoe"));
        verify(peopleRepositoryAdapter).findByUsername(Mockito.<String>any());
    }

    @Test
    void testFindByPersonId() {
        PersonModel personModel = new PersonModel(1, "janedoe", "jane.doe@example.org", "iloveyou", "Role");

        when(peopleRepositoryAdapter.findById(anyInt())).thenReturn(personModel);
        assertSame(personModel, personDetailsService.findByPersonId(1));
        verify(peopleRepositoryAdapter).findById(anyInt());
    }

    @Test
    void testFindByPersonId2() {
        when(peopleRepositoryAdapter.findById(anyInt())).thenThrow(new UsernameNotFoundException("Msg"));
        assertThrows(UsernameNotFoundException.class, () -> personDetailsService.findByPersonId(1));
        verify(peopleRepositoryAdapter).findById(anyInt());
    }

    @Test
    void testEdit() {
        doNothing().when(peopleRepositoryAdapter).save(Mockito.<PersonModel>any());
        PersonModel personModel = new PersonModel(1, "janedoe", "jane.doe@example.org", "iloveyou", "Role");

        personDetailsService.edit(personModel);
        verify(peopleRepositoryAdapter).save(Mockito.<PersonModel>any());
        assertEquals("jane.doe@example.org", personModel.getEmail());
        assertEquals("janedoe", personModel.getUsername());
        assertEquals("Role", personModel.getRole());
        assertEquals("iloveyou", personModel.getPassword());
        assertEquals(1, personModel.getId().intValue());
    }

    @Test
    void testEdit2() {
        doThrow(new UsernameNotFoundException("Msg")).when(peopleRepositoryAdapter).save(Mockito.<PersonModel>any());
        assertThrows(UsernameNotFoundException.class,
                () -> personDetailsService.edit(new PersonModel(1, "janedoe", "jane.doe@example.org", "iloveyou", "Role")));
        verify(peopleRepositoryAdapter).save(Mockito.<PersonModel>any());
    }
}

