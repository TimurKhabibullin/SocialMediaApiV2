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
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.timur.socialmediaapi.SocialMediaApi.adapter.PostRepositoryAdapter;
import ru.timur.socialmediaapi.SocialMediaApi.config.security.PersonDetails;
import ru.timur.socialmediaapi.SocialMediaApi.core.model.PersonModel;
import ru.timur.socialmediaapi.SocialMediaApi.core.model.PostModel;

@ContextConfiguration(classes = {PostService.class})
@ExtendWith(SpringExtension.class)
class PostServiceTest {
    @MockBean
    private PostRepositoryAdapter postRepositoryAdapter;

    @Autowired
    private PostService postService;

    @Test
    void testCreate() {
        PostModel postModel = new PostModel(1, "Header", "Text", "Link For Image", 1L, 1);

        when(postRepositoryAdapter.save(Mockito.<PostModel>any())).thenReturn(postModel);
        PostModel post = new PostModel(1, "Header", "Text", "Link For Image", 1L, 1);

        assertSame(postModel, postService.create(post, new TestingAuthenticationToken(
                new PersonDetails(new PersonModel(1, "janedoe", "jane.doe@example.org", "iloveyou", "Role")), "Credentials")));
        verify(postRepositoryAdapter).save(Mockito.<PostModel>any());
        assertEquals(1, post.getPerson());
    }

    @Test
    void testShowAll() {
        ArrayList<PostModel> postModelList = new ArrayList<>();
        when(postRepositoryAdapter.findAll()).thenReturn(postModelList);
        List<PostModel> actualShowAllResult = postService.showAll();
        assertSame(postModelList, actualShowAllResult);
        assertTrue(actualShowAllResult.isEmpty());
        verify(postRepositoryAdapter).findAll();
    }

    @Test
    void testFindById() {
        PostModel postModel = new PostModel(1, "Header", "Text", "Link For Image", 1L, 1);

        when(postRepositoryAdapter.findById(anyInt())).thenReturn(postModel);
        assertSame(postModel, postService.findById(1));
        verify(postRepositoryAdapter).findById(anyInt());
    }

    @Test
    void testUpdate() {
        PostModel postModel = new PostModel(1, "Header", "Text", "Link For Image", 1L, 1);

        when(postRepositoryAdapter.save(Mockito.<PostModel>any())).thenReturn(postModel);
        when(postRepositoryAdapter.findById(anyInt()))
                .thenReturn(new PostModel(1, "Header", "Text", "Link For Image", 1L, 1));
        assertSame(postModel, postService.update(new PostModel(1, "Header", "Text", "Link For Image", 1L, 1), 1));
        verify(postRepositoryAdapter).findById(anyInt());
        verify(postRepositoryAdapter).save(Mockito.<PostModel>any());
    }

    @Test
    void testUpdate3() {
        PostModel postModel = new PostModel(1, "Header", "Text", "Link For Image", 1L, 1);

        when(postRepositoryAdapter.save(Mockito.<PostModel>any())).thenReturn(postModel);
        when(postRepositoryAdapter.findById(anyInt()))
                .thenReturn(new PostModel(1, "Header", "Text", "Link For Image", 1L, 1));
        assertSame(postModel, postService.update(new PostModel(1, null, "Text", "Link For Image", 1L, 1), 1));
        verify(postRepositoryAdapter).findById(anyInt());
        verify(postRepositoryAdapter).save(Mockito.<PostModel>any());
    }

    @Test
    void testUpdate4() {
        PostModel postModel = new PostModel(1, "Header", "Text", "Link For Image", 1L, 1);

        when(postRepositoryAdapter.save(Mockito.<PostModel>any())).thenReturn(postModel);
        when(postRepositoryAdapter.findById(anyInt()))
                .thenReturn(new PostModel(1, "Header", "Text", "Link For Image", 1L, 1));
        assertSame(postModel, postService.update(new PostModel(1, "Header", null, "Link For Image", 1L, 1), 1));
        verify(postRepositoryAdapter).findById(anyInt());
        verify(postRepositoryAdapter).save(Mockito.<PostModel>any());
    }

    @Test
    void testUpdate5() {
        PostModel postModel = new PostModel(1, "Header", "Text", "Link For Image", 1L, 1);

        when(postRepositoryAdapter.save(Mockito.<PostModel>any())).thenReturn(postModel);
        when(postRepositoryAdapter.findById(anyInt()))
                .thenReturn(new PostModel(1, "Header", "Text", "Link For Image", 1L, 1));
        assertSame(postModel, postService.update(new PostModel(1, "Header", "Text", null, 1L, 1), 1));
        verify(postRepositoryAdapter).findById(anyInt());
        verify(postRepositoryAdapter).save(Mockito.<PostModel>any());
    }

    @Test
    void testDelete() {
        doNothing().when(postRepositoryAdapter).delete(Mockito.<PostModel>any());
        PostModel postToBeDeleted = new PostModel(1, "Header", "Text", "Link For Image", 1L, 1);

        assertSame(postToBeDeleted, postService.delete(postToBeDeleted));
        verify(postRepositoryAdapter).delete(Mockito.<PostModel>any());
    }

    @Test
    void testFindAllByPersonId() {
        ArrayList<PostModel> postModelList = new ArrayList<>();
        when(postRepositoryAdapter.findAllByPerson(anyInt())).thenReturn(postModelList);
        List<PostModel> actualFindAllByPersonIdResult = postService.findAllByPersonId(1);
        assertSame(postModelList, actualFindAllByPersonIdResult);
        assertTrue(actualFindAllByPersonIdResult.isEmpty());
        verify(postRepositoryAdapter).findAllByPerson(anyInt());
    }
}

