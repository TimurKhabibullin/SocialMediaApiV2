package ru.timur.socialmediaapi.SocialMediaApi.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.timur.socialmediaapi.SocialMediaApi.db.entity.Friendship;
import java.util.List;

@Repository
public interface FriendshipRepository extends JpaRepository<Friendship,Integer> {
    Friendship findById(int id);
    List<Friendship> findByUser1OrUser2(int user1Id,int user2Id);
    Friendship findByUser1AndUser2(int user1Id, int user2Id);
}
