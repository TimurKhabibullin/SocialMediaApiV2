package ru.timur.socialmediaapi.SocialMediaApi.core.service;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ru.timur.socialmediaapi.SocialMediaApi.adapter.PostRepositoryAdapter;
import ru.timur.socialmediaapi.SocialMediaApi.config.security.PersonDetails;
import ru.timur.socialmediaapi.SocialMediaApi.core.model.PersonModel;
import ru.timur.socialmediaapi.SocialMediaApi.core.model.PostModel;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PostService {

    private final PostRepositoryAdapter postRepositoryAdapter;

    public PostService(PostRepositoryAdapter postRepositoryAdapter) {
        this.postRepositoryAdapter = postRepositoryAdapter;
    }

    public PostModel create(PostModel post, Authentication authentication) {
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();

        PersonModel person = personDetails.getPerson();
        post.setPerson(person.getId());

        LocalDateTime currentDateTime = LocalDateTime.now();
        long timestamp = currentDateTime.toEpochSecond(java.time.ZoneOffset.UTC);
        post.setDateOfCreate(timestamp);

        return postRepositoryAdapter.save(post);
    }

    public List<PostModel> showAll() {
        return postRepositoryAdapter.findAll();
    }

    public PostModel findById(int id) {
        return postRepositoryAdapter.findById(id);
    }

    public PostModel update(PostModel post, int id) {
        PostModel postToBeUpdated = findById(id);
        if (post.getHeader() != null)
            postToBeUpdated.setHeader(post.getHeader());
        if (post.getText() != null)
            postToBeUpdated.setText(post.getText());
        if (post.getLinkForImage() != null)
            postToBeUpdated.setLinkForImage(post.getLinkForImage());
        return postRepositoryAdapter.save(post);
    }

    public PostModel delete(PostModel postToBeDeleted) {
        postRepositoryAdapter.delete(postToBeDeleted);
        return postToBeDeleted;
    }

    public List<PostModel> findAllByPersonId(int personId){
        return postRepositoryAdapter.findAllByPerson(personId);
    }
}
