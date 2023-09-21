package ru.timur.socialmediaapi.SocialMediaApi.core.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.timur.socialmediaapi.SocialMediaApi.adapter.FriendRequestRepositoryAdapter;
import ru.timur.socialmediaapi.SocialMediaApi.adapter.FriendshipRepositoryAdapter;
import ru.timur.socialmediaapi.SocialMediaApi.core.model.FriendRequestModel;
import ru.timur.socialmediaapi.SocialMediaApi.core.model.FriendshipModel;

@ContextConfiguration(classes = {FriendRequestService.class})
@ExtendWith(SpringExtension.class)
class FriendRequestServiceTest {
    @MockBean
    private FriendRequestRepositoryAdapter friendRequestRepositoryAdapter;

    @Autowired
    private FriendRequestService friendRequestService;

    @MockBean
    private FriendshipRepositoryAdapter friendshipRepositoryAdapter;

    @Test
    void testSendFriendRequest() {
        FriendRequestModel friendRequestModel = new FriendRequestModel(1, 3, 1, "Status");

        when(friendRequestRepositoryAdapter.save(Mockito.<FriendRequestModel>any())).thenReturn(friendRequestModel);
        assertSame(friendRequestModel, friendRequestService.sendFriendRequest(1, 1));
        verify(friendRequestRepositoryAdapter).save(Mockito.<FriendRequestModel>any());
    }

    @Test
    void testSendFriendRequest2() {
        FriendRequestModel friendRequestModel = new FriendRequestModel(1, 3, 1, "Status");

        when(friendRequestRepositoryAdapter.save(Mockito.<FriendRequestModel>any())).thenReturn(friendRequestModel);
        FriendRequestModel friendRequestModel2 = new FriendRequestModel(1, 3, 1, "Status");

        assertSame(friendRequestModel, friendRequestService.sendFriendRequest(friendRequestModel2));
        verify(friendRequestRepositoryAdapter).save(Mockito.<FriendRequestModel>any());
        assertEquals("PENDING", friendRequestModel2.getStatus());
    }

    @Test
    void testAcceptFriendRequest() {
        doNothing().when(friendRequestRepositoryAdapter).delete(Mockito.<FriendRequestModel>any());
        FriendRequestModel friendRequestModel = new FriendRequestModel(1, 3, 1, "Status");

        when(friendRequestRepositoryAdapter.findById(anyInt())).thenReturn(friendRequestModel);
        doNothing().when(friendshipRepositoryAdapter).save(Mockito.<FriendshipModel>any());
        assertSame(friendRequestModel, friendRequestService.acceptFriendRequest(1));
        verify(friendRequestRepositoryAdapter).findById(anyInt());
        verify(friendRequestRepositoryAdapter).delete(Mockito.<FriendRequestModel>any());
        verify(friendshipRepositoryAdapter).save(Mockito.<FriendshipModel>any());
    }

    @Test
    void testRejectFriendRequest() {
        FriendRequestModel friendRequestModel = new FriendRequestModel(1, 3, 1, "Status");

        when(friendRequestRepositoryAdapter.findById(anyInt())).thenReturn(friendRequestModel);
        FriendRequestModel actualRejectFriendRequestResult = friendRequestService.rejectFriendRequest(1);
        assertSame(friendRequestModel, actualRejectFriendRequestResult);
        assertEquals("REJECTED", actualRejectFriendRequestResult.getStatus());
        verify(friendRequestRepositoryAdapter).findById(anyInt());
    }

    @Test
    void testGetFriendRequestById() {
        FriendRequestModel friendRequestModel = new FriendRequestModel(1, 3, 1, "Status");

        when(friendRequestRepositoryAdapter.findById(anyInt())).thenReturn(friendRequestModel);
        assertSame(friendRequestModel, friendRequestService.getFriendRequestById(1));
        verify(friendRequestRepositoryAdapter).findById(anyInt());
    }

    @Test
    void testFindBySender() {
        ArrayList<FriendRequestModel> friendRequestModelList = new ArrayList<>();
        when(friendRequestRepositoryAdapter.findBySender(anyInt())).thenReturn(friendRequestModelList);
        List<FriendRequestModel> actualFindBySenderResult = friendRequestService.findBySender(1);
        assertSame(friendRequestModelList, actualFindBySenderResult);
        assertTrue(actualFindBySenderResult.isEmpty());
        verify(friendRequestRepositoryAdapter).findBySender(anyInt());
    }

    @Test
    void testRemove() {
        doNothing().when(friendRequestRepositoryAdapter).delete(Mockito.<FriendRequestModel>any());
        FriendRequestModel friendRequestModel = new FriendRequestModel(1, 3, 1, "Status");

        assertSame(friendRequestModel, friendRequestService.remove(friendRequestModel));
        verify(friendRequestRepositoryAdapter).delete(Mockito.<FriendRequestModel>any());
    }
}

