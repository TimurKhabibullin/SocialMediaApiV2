package ru.timur.socialmediaapi.SocialMediaApi.core.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.timur.socialmediaapi.SocialMediaApi.adapter.PeopleRepositoryAdapter;
import ru.timur.socialmediaapi.SocialMediaApi.core.model.PersonModel;
import ru.timur.socialmediaapi.SocialMediaApi.config.security.PersonDetails;
import ru.timur.socialmediaapi.SocialMediaApi.core.model.FriendRequestModel;
import ru.timur.socialmediaapi.SocialMediaApi.core.model.FriendshipModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PersonDetailsService implements UserDetailsService {
    private final PeopleRepositoryAdapter peopleRepositoryAdapter;
    private final FriendshipService friendshipService;
    private final FriendRequestService friendRequestService;

    public PersonDetailsService(PeopleRepositoryAdapter peopleRepositoryAdapter, FriendshipService friendshipService, FriendRequestService friendRequestService) {
        this.peopleRepositoryAdapter = peopleRepositoryAdapter;
        this.friendshipService = friendshipService;
        this.friendRequestService = friendRequestService;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<PersonModel> person = Optional.ofNullable(peopleRepositoryAdapter.findByUsername(username));
        if (person.isEmpty())
            throw new UsernameNotFoundException("User not found");

        return new PersonDetails(person.get());
    }

    public PersonModel findByUsername(String username) {
        return peopleRepositoryAdapter.findByUsername(username);
    }

    public PersonModel findByPersonId(int id) {
        return peopleRepositoryAdapter.findById(id);
    }

    @Transactional
    public void edit(PersonModel personModel) {
        peopleRepositoryAdapter.save(personModel);
    }

    public List<Integer> getPersonPostsId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        PersonModel person = personDetails.getPerson();
        List<Integer> personsPostsId = new ArrayList<>();
        List<FriendshipModel> friendships = friendshipService.findByUser1OrUser2(person.getId(), person.getId());
        List<FriendRequestModel> friendRequests = friendRequestService.findBySender(person.getId());

        for (FriendshipModel friendship : friendships) {
            if (friendship.getUser1() == person.getId()) {
                personsPostsId.add(friendship.getUser2());
            } else {
                personsPostsId.add(friendship.getUser1());
            }
        }
        for (FriendRequestModel friendRequest : friendRequests) {
            personsPostsId.add(friendRequest.getRecipient());
        }
        return personsPostsId;
    }
}
