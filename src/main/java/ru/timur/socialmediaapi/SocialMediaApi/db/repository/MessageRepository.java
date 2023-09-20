package ru.timur.socialmediaapi.SocialMediaApi.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.timur.socialmediaapi.SocialMediaApi.db.entity.Message;
import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {
    List<Message> findBySenderAndRecipient(int senderId, int recipientId);
}
