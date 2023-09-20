package ru.timur.socialmediaapi.SocialMediaApi.adapter.mapper;

import org.springframework.stereotype.Component;
import ru.timur.socialmediaapi.SocialMediaApi.core.model.PostModel;
import ru.timur.socialmediaapi.SocialMediaApi.db.entity.Post;

@Component
public class PostMapper {
    public PostModel mapToModel(Post post){
        return new PostModel(post.getId(), post.getHeader(), post.getText(), post.getLinkForImage(), post.getDateOfCreate(), post.getPerson());
    }

    public Post mapToEntity(PostModel post){
        return new Post(post.getId(), post.getHeader(), post.getText(), post.getLinkForImage(), post.getDateOfCreate(), post.getPerson());
    }
}
