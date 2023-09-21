package ru.timur.socialmediaapi.SocialMediaApi.core.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.timur.socialmediaapi.SocialMediaApi.adapter.FriendshipRepositoryAdapter;
import ru.timur.socialmediaapi.SocialMediaApi.core.model.FriendshipModel;

@ContextConfiguration(classes = {FriendshipService.class})
@ExtendWith(SpringExtension.class)
class FriendshipServiceTest {
    @MockBean
    private FriendRequestService friendRequestService;

    @MockBean
    private FriendshipRepositoryAdapter friendshipRepositoryAdapter;

    @Autowired
    private FriendshipService friendshipService;

    @Test
    void testGetFriendshipById() {
        FriendshipModel friendshipModel = new FriendshipModel(3, 1);

        when(friendshipRepositoryAdapter.findById(anyInt())).thenReturn(friendshipModel);
        assertSame(friendshipModel, friendshipService.getFriendshipById(1));
        verify(friendshipRepositoryAdapter).findById(anyInt());
    }

    @Test
    void testFindByUser1OrUser2() {
        ArrayList<FriendshipModel> friendshipModelList = new ArrayList<>();
        when(friendshipRepositoryAdapter.findByUser1OrUser2(anyInt(), anyInt())).thenReturn(friendshipModelList);
        List<FriendshipModel> actualFindByUser1OrUser2Result = friendshipService.findByUser1OrUser2(1, 1);
        assertSame(friendshipModelList, actualFindByUser1OrUser2Result);
        assertTrue(actualFindByUser1OrUser2Result.isEmpty());
        verify(friendshipRepositoryAdapter).findByUser1OrUser2(anyInt(), anyInt());
    }

    @Test
    void testFindByUser1AndUser2() {
        FriendshipModel friendshipModel = new FriendshipModel(3, 1);

        when(friendshipRepositoryAdapter.findByUser1AndUser2(anyInt(), anyInt())).thenReturn(friendshipModel);
        assertSame(friendshipModel, friendshipService.findByUser1AndUser2(1, 1));
        verify(friendshipRepositoryAdapter).findByUser1AndUser2(anyInt(), anyInt());
    }

    @Test
    void testIsFriends() {
        when(friendshipRepositoryAdapter.findByUser1AndUser2(anyInt(), anyInt())).thenReturn(new FriendshipModel(3, 1));
        assertTrue(friendshipService.isFriends(1, 1));
        verify(friendshipRepositoryAdapter).findByUser1AndUser2(anyInt(), anyInt());
    }

    @Test
    void testIsFriends2() {
        when(friendshipRepositoryAdapter.findByUser1AndUser2(anyInt(), anyInt())).thenReturn(null);
        assertFalse(friendshipService.isFriends(1, 1));
        verify(friendshipRepositoryAdapter, atLeast(1)).findByUser1AndUser2(anyInt(), anyInt());
    }
}

