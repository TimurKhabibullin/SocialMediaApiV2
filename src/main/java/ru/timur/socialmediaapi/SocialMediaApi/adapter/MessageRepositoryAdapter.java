package ru.timur.socialmediaapi.SocialMediaApi.adapter;

import org.springframework.stereotype.Component;
import ru.timur.socialmediaapi.SocialMediaApi.core.model.MessageModel;
import ru.timur.socialmediaapi.SocialMediaApi.db.repository.MessageRepository;
import ru.timur.socialmediaapi.SocialMediaApi.adapter.mapper.MessageMapper;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MessageRepositoryAdapter {
    private final MessageRepository messageRepository;
    private final MessageMapper messageMapper;

    public MessageRepositoryAdapter(MessageRepository messageRepository, MessageMapper messageMapper) {
        this.messageRepository = messageRepository;
        this.messageMapper = messageMapper;
    }

    public List<MessageModel> findBySenderAndRecipient(int senderId, int recipientId){
        return messageRepository.findBySenderAndRecipient(senderId,recipientId).stream().map(message -> messageMapper.mapToModel(message)).collect(Collectors.toList());
    }

    public MessageModel save(MessageModel messageModel) {
        messageRepository.save(messageMapper.mapToEntity(messageModel));
        return messageModel;
    }
}
