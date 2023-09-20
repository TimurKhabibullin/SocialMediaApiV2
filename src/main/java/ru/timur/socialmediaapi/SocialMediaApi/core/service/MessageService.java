package ru.timur.socialmediaapi.SocialMediaApi.core.service;

import org.springframework.stereotype.Service;
import ru.timur.socialmediaapi.SocialMediaApi.adapter.MessageRepositoryAdapter;
import ru.timur.socialmediaapi.SocialMediaApi.core.model.MessageModel;
import ru.timur.socialmediaapi.SocialMediaApi.core.model.PersonModel;
import java.util.ArrayList;
import java.util.List;

@Service
public class MessageService {
    private final MessageRepositoryAdapter messageRepositoryAdapter;

    public MessageService(MessageRepositoryAdapter messageRepositoryAdapter) {
        this.messageRepositoryAdapter = messageRepositoryAdapter;
    }

    public MessageModel sendMessage(MessageModel messageModel) {
        return messageRepositoryAdapter.save(messageModel);
    }

    public List<MessageModel> getMessages(PersonModel user1, PersonModel user2) {
        List<MessageModel> messageModels = new ArrayList<>();

        messageModels.addAll(messageRepositoryAdapter.findBySenderAndRecipient(user1.getId(),user2.getId()));
        messageModels.addAll(messageRepositoryAdapter.findBySenderAndRecipient(user2.getId(), user1.getId()));

        return messageModels;
    }
}
