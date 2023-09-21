package ru.timur.socialmediaapi.SocialMediaApi.core.service;

import org.springframework.stereotype.Service;
import ru.timur.socialmediaapi.SocialMediaApi.adapter.FriendRequestRepositoryAdapter;
import ru.timur.socialmediaapi.SocialMediaApi.adapter.FriendshipRepositoryAdapter;
import ru.timur.socialmediaapi.SocialMediaApi.core.model.FriendRequestModel;
import ru.timur.socialmediaapi.SocialMediaApi.core.model.FriendshipModel;
import ru.timur.socialmediaapi.SocialMediaApi.core.model.RequestStatusModel;
import java.util.List;

@Service
public class FriendRequestService {
    private final FriendRequestRepositoryAdapter friendRequestRepositoryAdapter;
    private final FriendshipRepositoryAdapter friendshipRepositoryAdapter;

    public FriendRequestService(FriendRequestRepositoryAdapter friendRequestRepositoryAdapter, FriendshipRepositoryAdapter friendshipRepositoryAdapter) {
        this.friendRequestRepositoryAdapter = friendRequestRepositoryAdapter;
        this.friendshipRepositoryAdapter = friendshipRepositoryAdapter;
    }


    public FriendRequestModel sendFriendRequest(int senderId, int recipientId) {
        FriendRequestModel friendRequestModel = new FriendRequestModel();
        friendRequestModel.setSender(senderId);
        friendRequestModel.setRecipient(recipientId);
        friendRequestModel.setStatus(RequestStatusModel.PENDING.toString());
        return friendRequestRepositoryAdapter.save(friendRequestModel);
    }

    public FriendRequestModel sendFriendRequest(FriendRequestModel friendRequestModel) {
        friendRequestModel.setStatus(RequestStatusModel.PENDING.toString());
        return friendRequestRepositoryAdapter.save(friendRequestModel);
    }

    public FriendRequestModel acceptFriendRequest(int friendRequestId) {
        FriendRequestModel friendRequestModel = friendRequestRepositoryAdapter.findById(friendRequestId);

        // Создание дружбы между пользователями
        FriendshipModel friendshipModel = new FriendshipModel(friendRequestModel.getSender(), friendRequestModel.getRecipient());
        friendshipModel.setUser1(friendRequestModel.getSender());
        friendshipModel.setUser2(friendRequestModel.getRecipient());
        friendshipRepositoryAdapter.save(friendshipModel);

        friendRequestRepositoryAdapter.delete(friendRequestModel);

        return friendRequestModel;
    }

    public FriendRequestModel rejectFriendRequest(int friendRequestId) {
        FriendRequestModel friendRequestModel = friendRequestRepositoryAdapter.findById(friendRequestId);
        friendRequestModel.setStatus(RequestStatusModel.REJECTED.toString());
        return friendRequestModel;
    }

    public FriendRequestModel getFriendRequestById(int requestId) {
        return friendRequestRepositoryAdapter.findById(requestId);
    }

    public List<FriendRequestModel> findBySender(int senderId){
        return friendRequestRepositoryAdapter.findBySender(senderId);
    }

    public FriendRequestModel remove(FriendRequestModel friendRequestModel){
        friendRequestRepositoryAdapter.delete(friendRequestModel);
        return friendRequestModel;
    }
}