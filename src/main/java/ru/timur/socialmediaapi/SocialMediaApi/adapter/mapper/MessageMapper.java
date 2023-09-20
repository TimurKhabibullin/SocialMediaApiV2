package ru.timur.socialmediaapi.SocialMediaApi.adapter.mapper;

import org.springframework.stereotype.Component;
import ru.timur.socialmediaapi.SocialMediaApi.core.model.MessageModel;
import ru.timur.socialmediaapi.SocialMediaApi.db.entity.Message;

@Component
public class MessageMapper {
    public MessageModel mapToModel(Message message){
        return new MessageModel(message.getId(), message.getSender(), message.getRecipient(), message.getText());
    }

    public Message mapToEntity(MessageModel message){
        return new Message(message.getId(), message.getSender(), message.getRecipient(), message.getText());
    }
}
