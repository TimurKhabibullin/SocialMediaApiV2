package ru.timur.socialmediaapi.SocialMediaApi.core.service;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.timur.socialmediaapi.SocialMediaApi.adapter.MessageRepositoryAdapter;
import ru.timur.socialmediaapi.SocialMediaApi.core.model.MessageModel;
import ru.timur.socialmediaapi.SocialMediaApi.core.model.PersonModel;

@ContextConfiguration(classes = {MessageService.class})
@ExtendWith(SpringExtension.class)
class MessageServiceTest {
    @MockBean
    private MessageRepositoryAdapter messageRepositoryAdapter;

    @Autowired
    private MessageService messageService;

    @Test
    void testSendMessage() {
        MessageModel messageModel = new MessageModel(1, 3, 1, "Text");

        when(messageRepositoryAdapter.save(Mockito.<MessageModel>any())).thenReturn(messageModel);
        assertSame(messageModel, messageService.sendMessage(new MessageModel(1, 3, 1, "Text")));
        verify(messageRepositoryAdapter).save(Mockito.<MessageModel>any());
    }

    @Test
    void testGetMessages() {
        when(messageRepositoryAdapter.findBySenderAndRecipient(anyInt(), anyInt())).thenReturn(new ArrayList<>());
        PersonModel user1 = new PersonModel(1, "janedoe", "jane.doe@example.org", "iloveyou", "Role");

        assertTrue(
                messageService.getMessages(user1, new PersonModel(1, "janedoe", "jane.doe@example.org", "iloveyou", "Role"))
                        .isEmpty());
        verify(messageRepositoryAdapter, atLeast(1)).findBySenderAndRecipient(anyInt(), anyInt());
    }
}

