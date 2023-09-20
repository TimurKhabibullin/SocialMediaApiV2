package ru.timur.socialmediaapi.SocialMediaApi.adapter;

import org.springframework.stereotype.Component;
import ru.timur.socialmediaapi.SocialMediaApi.core.model.FriendshipModel;
import ru.timur.socialmediaapi.SocialMediaApi.db.repository.FriendshipRepository;
import ru.timur.socialmediaapi.SocialMediaApi.adapter.mapper.FriendShipMapper;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FriendshipRepositoryAdapter {
    private final FriendshipRepository friendshipRepository;
    private final FriendShipMapper friendShipMapper;

    public FriendshipRepositoryAdapter(FriendshipRepository friendshipRepository, FriendShipMapper friendShipMapper) {
        this.friendshipRepository = friendshipRepository;
        this.friendShipMapper = friendShipMapper;
    }

    public FriendshipModel findById(int id){
        return friendShipMapper.mapToModel(friendshipRepository.findById(id));
    }

    public List<FriendshipModel> findByUser1OrUser2(int user1Id,int user2Id){
        return friendshipRepository.findByUser1OrUser2(user1Id,user2Id).stream().map(friendship -> friendShipMapper.mapToModel(friendship)).collect(Collectors.toList());
    }

    public FriendshipModel findByUser1AndUser2(int user1Id, int user2Id){
        return friendShipMapper.mapToModel(friendshipRepository.findByUser1AndUser2(user1Id,user2Id));
    }

    public void save(FriendshipModel friendshipModel) {
        friendshipRepository.save(friendShipMapper.mapToEntity(friendshipModel));
    }

    public void delete(FriendshipModel friendshipModel) {
        friendshipRepository.delete(friendShipMapper.mapToEntity(friendshipModel));
    }
}
