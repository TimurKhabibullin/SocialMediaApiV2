package ru.timur.socialmediaapi.SocialMediaApi.adapter.mapper;

import org.springframework.stereotype.Component;
import ru.timur.socialmediaapi.SocialMediaApi.core.model.FriendshipModel;
import ru.timur.socialmediaapi.SocialMediaApi.db.entity.Friendship;

@Component
public class FriendShipMapper {
    public FriendshipModel mapToModel(Friendship friendship){
        return new FriendshipModel(friendship.getId(), friendship.getUser1(), friendship.getUser2());
    }

    public Friendship mapToEntity(FriendshipModel friendshipModel){
        return new Friendship(friendshipModel.getId(), friendshipModel.getUser1(), friendshipModel.getUser2());
    }
}
