package ru.timur.socialmediaapi.SocialMediaApi.adapter.mapper;

import org.springframework.stereotype.Component;
import ru.timur.socialmediaapi.SocialMediaApi.core.model.FriendRequestModel;
import ru.timur.socialmediaapi.SocialMediaApi.db.entity.FriendRequest;

@Component
public class FriendRequestMapper {
    public FriendRequestModel mapToModel(FriendRequest friendRequest){
        return new FriendRequestModel(friendRequest.getId(), friendRequest.getSender(), friendRequest.getRecipient(), friendRequest.getStatus());
    }

    public FriendRequest mapToEntity(FriendRequestModel friendRequest){
        return new FriendRequest(friendRequest.getId(), friendRequest.getSender(), friendRequest.getRecipient(), friendRequest.getStatus());
    }
}
