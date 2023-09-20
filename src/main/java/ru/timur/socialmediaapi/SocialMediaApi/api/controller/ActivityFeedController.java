package ru.timur.socialmediaapi.SocialMediaApi.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.timur.socialmediaapi.SocialMediaApi.config.security.PersonDetails;
import ru.timur.socialmediaapi.SocialMediaApi.core.model.FriendRequestModel;
import ru.timur.socialmediaapi.SocialMediaApi.core.model.FriendshipModel;
import ru.timur.socialmediaapi.SocialMediaApi.core.model.PersonModel;
import ru.timur.socialmediaapi.SocialMediaApi.core.model.PostModel;
import ru.timur.socialmediaapi.SocialMediaApi.core.service.FriendRequestService;
import ru.timur.socialmediaapi.SocialMediaApi.core.service.FriendshipService;
import ru.timur.socialmediaapi.SocialMediaApi.core.service.PostService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.List;

@RestController
@RequestMapping("/activitys")
@Tag(name = "ActivityFeed", description = "Auth management APIs")
@RequiredArgsConstructor
public class ActivityFeedController {

    private final PostService postService;
    private final FriendshipService friendshipService;
    private final FriendRequestService friendRequestService;

    @GetMapping()
    @Operation(summary = "Отображение последних постов")
    @SecurityRequirement(name = "bearerAuth")
    public List<PostModel> showActivity(@RequestParam(value = "offset", required = false) Integer offset,
                                        @RequestParam(value = "limit", required = false) Integer limit,
                                        HttpServletRequest request){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();

        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            String headerValue = request.getHeader(headerName);
            System.out.println(headerName + ": " + headerValue);
        }

        // Получить значение конкретного заголовка
        String contentType = request.getHeader("Content-Type");
        System.out.println("Content-Type: " + contentType);

        PersonModel person = personDetails.getPerson();
        List<Integer> personsPostsId = new ArrayList<>();
        List<FriendshipModel> friendships = friendshipService.findByUser1OrUser2(person.getId(),person.getId());
        List<FriendRequestModel> friendRequests = friendRequestService.findBySender(person.getId());

        for(FriendshipModel friendship : friendships){
            if (friendship.getUser1() == person.getId()) {
                personsPostsId.add(friendship.getUser2());
            } else {
                personsPostsId.add(friendship.getUser1());
            }
        }
        for (FriendRequestModel friendRequest : friendRequests){
            personsPostsId.add(friendRequest.getRecipient());
        }

        List<PostModel> posts = new ArrayList<>();
        for (Integer integer : personsPostsId) {
            posts.addAll(postService.findAllByPersonId(integer));
        }
        posts.sort(Comparator.comparingLong(PostModel::getDateOfCreate));
        Collections.reverse(posts);
        if (offset != null && limit != null){
            return getPostsByPage(posts,offset,limit);
        }
        return posts;
    }

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
