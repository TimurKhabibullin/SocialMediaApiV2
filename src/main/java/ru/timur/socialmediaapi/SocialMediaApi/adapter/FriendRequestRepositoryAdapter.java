package ru.timur.socialmediaapi.SocialMediaApi.adapter;

import org.springframework.stereotype.Component;
import ru.timur.socialmediaapi.SocialMediaApi.core.model.FriendRequestModel;
import ru.timur.socialmediaapi.SocialMediaApi.db.repository.FriendRequestRepository;
import ru.timur.socialmediaapi.SocialMediaApi.adapter.mapper.FriendRequestMapper;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FriendRequestRepositoryAdapter {
    private final FriendRequestRepository friendRequestRepository;
    private final FriendRequestMapper friendRequestMapper;

    public FriendRequestRepositoryAdapter(FriendRequestRepository friendRequestRepository, FriendRequestMapper friendRequestMapper) {
        this.friendRequestRepository = friendRequestRepository;
        this.friendRequestMapper = friendRequestMapper;
    }

    public FriendRequestModel findById(int id){
        return friendRequestMapper.mapToModel(friendRequestRepository.findById(id));
    }

    public List<FriendRequestModel> findBySender(int senderId){
        return friendRequestRepository.findBySender(senderId).stream().map(friendRequest -> friendRequestMapper.mapToModel(friendRequest)).collect(Collectors.toList());
    }

    public FriendRequestModel save(FriendRequestModel friendRequestModel) {
        friendRequestRepository.save(friendRequestMapper.mapToEntity(friendRequestModel));
        return friendRequestModel;
    }

    public void delete(FriendRequestModel friendRequestModel) {
        friendRequestRepository.delete(friendRequestMapper.mapToEntity(friendRequestModel));
    }
}
