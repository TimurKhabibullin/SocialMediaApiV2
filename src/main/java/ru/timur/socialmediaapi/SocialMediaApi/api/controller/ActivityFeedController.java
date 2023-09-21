package ru.timur.socialmediaapi.SocialMediaApi.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.timur.socialmediaapi.SocialMediaApi.core.model.PostModel;
import ru.timur.socialmediaapi.SocialMediaApi.core.service.PersonDetailsService;
import ru.timur.socialmediaapi.SocialMediaApi.core.service.PostService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/activitys")
@Tag(name = "ActivityFeed", description = "Auth management APIs")
@RequiredArgsConstructor
public class ActivityFeedController {

    private final PostService postService;
    private final PersonDetailsService personDetailsService;

    @GetMapping()
    @Operation(summary = "Отображение последних постов")
    @SecurityRequirement(name = "bearerAuth")
    public List<PostModel> showActivity(@RequestParam(value = "offset", required = false) Integer offset,
                                        @RequestParam(value = "limit", required = false) Integer limit){

        List<PostModel> posts = new ArrayList<>();
        for (Integer integer : personDetailsService.getPersonPostsId()) {
            posts.addAll(postService.findAllByPersonId(integer));
        }
        posts.sort(Comparator.comparingLong(PostModel::getDateOfCreate));
        Collections.reverse(posts);
        return getPostsByPage(posts,offset,limit);
    }

    // Пагинация страниц
    public List<PostModel> getPostsByPage(List<PostModel> posts, int pageNumber, int pageSize) {
        int startIndex = pageNumber * pageSize;
        int endIndex = Math.min(startIndex + pageSize, posts.size());

        // Проверка на выход за пределы списка
        if (startIndex >= posts.size()) {
            return new ArrayList<>();
        }

        // Получение подсписка с учетом пагинации
        return posts.subList(startIndex, endIndex);
    }
}
