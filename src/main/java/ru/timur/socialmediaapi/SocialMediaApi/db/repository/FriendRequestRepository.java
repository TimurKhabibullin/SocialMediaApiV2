package ru.timur.socialmediaapi.SocialMediaApi.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.timur.socialmediaapi.SocialMediaApi.db.entity.FriendRequest;
import java.util.List;

@Repository
public interface FriendRequestRepository extends JpaRepository<FriendRequest, Integer> {
    FriendRequest findById(int id);
    List<FriendRequest> findBySender(int senderId);
}
