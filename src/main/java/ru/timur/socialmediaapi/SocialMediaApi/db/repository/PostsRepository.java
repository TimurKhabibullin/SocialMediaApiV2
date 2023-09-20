package ru.timur.socialmediaapi.SocialMediaApi.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.timur.socialmediaapi.SocialMediaApi.db.entity.Post;
import java.util.List;

@Repository
public interface PostsRepository extends JpaRepository<Post,Integer> {

    Post findById(int id);

    List<Post> findAllByPerson(int personId);
}
