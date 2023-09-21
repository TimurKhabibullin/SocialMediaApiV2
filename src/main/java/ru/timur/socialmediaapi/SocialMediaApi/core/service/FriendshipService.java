package ru.timur.socialmediaapi.SocialMediaApi.core.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.timur.socialmediaapi.SocialMediaApi.adapter.FriendshipRepositoryAdapter;
import ru.timur.socialmediaapi.SocialMediaApi.config.security.PersonDetails;
import ru.timur.socialmediaapi.SocialMediaApi.core.model.FriendRequestModel;
import ru.timur.socialmediaapi.SocialMediaApi.core.model.FriendshipModel;
import ru.timur.socialmediaapi.SocialMediaApi.core.model.PersonModel;
import ru.timur.socialmediaapi.SocialMediaApi.core.model.RequestStatusModel;

import java.util.List;
import java.util.Optional;

@Service
public class FriendshipService {
    private final FriendshipRepositoryAdapter friendshipRepositoryAdapter;
    private final FriendRequestService friendRequestService;


    public FriendshipService(FriendshipRepositoryAdapter friendshipRepositoryAdapter, FriendRequestService friendRequestService) {
        this.friendshipRepositoryAdapter = friendshipRepositoryAdapter;
        this.friendRequestService = friendRequestService;
    }


    public FriendshipModel getFriendshipById(int friendshipId) {
        return friendshipRepositoryAdapter.findById(friendshipId);
    }

    public FriendshipModel removeFriendship(FriendshipModel friendshipModel) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();

        PersonModel person = personDetails.getPerson();

        // Удаление дружбы
        FriendRequestModel friendRequest = new FriendRequestModel();
        friendRequest.setRecipient(person.getId());
        if (person.getId() == friendshipModel.getUser1()){
            friendRequest.setSender(friendshipModel.getUser2());
        } else {
            friendRequest.setSender(friendshipModel.getUser1());
        }
        friendRequest.setStatus(RequestStatusModel.PENDING.toString());
        friendRequestService.sendFriendRequest(friendRequest);

        friendshipRepositoryAdapter.delete(friendshipModel);

        return friendshipModel;
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