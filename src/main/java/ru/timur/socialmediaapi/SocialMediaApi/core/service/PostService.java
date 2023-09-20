package ru.timur.socialmediaapi.SocialMediaApi.core.service;

import org.springframework.stereotype.Service;
import ru.timur.socialmediaapi.SocialMediaApi.adapter.PostRepositoryAdapter;
import ru.timur.socialmediaapi.SocialMediaApi.core.model.PostModel;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PostService {

    private final PostRepositoryAdapter postRepositoryAdapter;

    public PostService(PostRepositoryAdapter postRepositoryAdapter) {
        this.postRepositoryAdapter = postRepositoryAdapter;
    }

    public PostModel create(PostModel post) {
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

    public PostModel update(PostModel post) {
        return postRepositoryAdapter.save(post);
    }

    public void delete(PostModel postToBeDeleted) {
        postRepositoryAdapter.delete(postToBeDeleted);
    }

    public List<PostModel> findAllByPersonId(int personId){
        return postRepositoryAdapter.findAllByPerson(personId);
    }
}
