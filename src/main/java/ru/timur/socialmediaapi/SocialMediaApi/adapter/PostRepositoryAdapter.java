package ru.timur.socialmediaapi.SocialMediaApi.adapter;

import org.springframework.stereotype.Component;
import ru.timur.socialmediaapi.SocialMediaApi.core.model.PostModel;
import ru.timur.socialmediaapi.SocialMediaApi.db.repository.PostsRepository;
import ru.timur.socialmediaapi.SocialMediaApi.adapter.mapper.PostMapper;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PostRepositoryAdapter {
    private final PostsRepository postsRepository;
    private final PostMapper postMapper;

    public PostRepositoryAdapter(PostsRepository postsRepository, PostMapper postMapper) {
        this.postsRepository = postsRepository;
        this.postMapper = postMapper;
    }

    public PostModel findById(int id){
        return postMapper.mapToModel(postsRepository.findById(id));
    }

    public List<PostModel> findAllByPerson(int personId){
        return postsRepository.findAllByPerson(personId).stream().map(post -> postMapper.mapToModel(post)).collect(Collectors.toList());
    }

    public PostModel save(PostModel post) {
        postsRepository.save(postMapper.mapToEntity(post));
        return post;
    }

    public List<PostModel> findAll() {
        return postsRepository.findAll().stream().map(post -> postMapper.mapToModel(post)).collect(Collectors.toList());
    }

    public void delete(PostModel postToBeDeleted) {
        postsRepository.delete(postMapper.mapToEntity(postToBeDeleted));
    }
}
