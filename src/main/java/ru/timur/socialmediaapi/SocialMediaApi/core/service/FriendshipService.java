package ru.timur.socialmediaapi.SocialMediaApi.core.service;

import org.springframework.stereotype.Service;
import ru.timur.socialmediaapi.SocialMediaApi.adapter.FriendshipRepositoryAdapter;
import ru.timur.socialmediaapi.SocialMediaApi.core.model.FriendshipModel;
import java.util.List;

@Service
public class FriendshipService {
    private final FriendshipRepositoryAdapter friendshipRepositoryAdapter;

    public FriendshipService(FriendshipRepositoryAdapter friendshipRepositoryAdapter) {
        this.friendshipRepositoryAdapter = friendshipRepositoryAdapter;
    }


    public FriendshipModel getFriendshipById(int friendshipId) {
        return friendshipRepositoryAdapter.findById(friendshipId);
    }

    public void removeFriendship(FriendshipModel friendshipModel) {
        friendshipRepositoryAdapter.delete(friendshipModel);
    }

    public List<FriendshipModel> findByUser1OrUser2(int user1Id, int user2Id){
        return friendshipRepositoryAdapter.findByUser1OrUser2(user1Id,user2Id);
    }

    public FriendshipModel findByUser1AndUser2(int user1Id, int user2Id){
        return friendshipRepositoryAdapter.findByUser1AndUser2(user1Id,user2Id);
    }

    public boolean isFriends(int senderId, int recipientId) {
        return friendshipRepositoryAdapter.findByUser1AndUser2(senderId, recipientId) != null ||
                friendshipRepositoryAdapter.findByUser1AndUser2(recipientId, senderId) != null;
    }
}