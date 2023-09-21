package ru.timur.socialmediaapi.SocialMediaApi.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.timur.socialmediaapi.SocialMediaApi.api.dto.MessageDTO;
import ru.timur.socialmediaapi.SocialMediaApi.core.model.FriendRequestModel;
import ru.timur.socialmediaapi.SocialMediaApi.core.model.FriendshipModel;
import ru.timur.socialmediaapi.SocialMediaApi.core.model.MessageModel;
import ru.timur.socialmediaapi.SocialMediaApi.core.service.FriendRequestService;
import ru.timur.socialmediaapi.SocialMediaApi.core.service.FriendshipService;
import ru.timur.socialmediaapi.SocialMediaApi.core.service.MessageService;
import ru.timur.socialmediaapi.SocialMediaApi.core.service.PersonDetailsService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/friends")
@RequiredArgsConstructor
public class FriendController {

    private final FriendRequestService friendRequestService;
    private final FriendshipService friendshipService;
    private final MessageService messageService;
    private final ModelMapper modelMapper;
    private final PersonDetailsService personDetailsService;


    @Operation(summary = "Отправка заявки в друзья")
    @PostMapping("/requests")
    @SecurityRequirement(name = "bearerAuth")
    public FriendRequestModel sendFriendRequest(@RequestParam(value = "senderId", required = false) Integer senderId,
                                           @RequestParam(value = "recipientId", required = false) Integer recipientId) {
        return friendRequestService.sendFriendRequest(senderId,recipientId);
    }

    @Operation(summary = "Принятие заявки в друзья")
    @PostMapping("/requests/{requestId}/accept")
    @SecurityRequirement(name = "bearerAuth")
    public FriendRequestModel acceptFriendRequest(@PathVariable(value = "requestId", required = false) Integer requestId) {
        return friendRequestService.acceptFriendRequest(friendRequestService.getFriendRequestById(requestId).getId());
    }

    @Operation(summary = "Отклонение заявки в друзья")
    @PostMapping("/requests/{requestId}/reject")
    @SecurityRequirement(name = "bearerAuth")
    public FriendRequestModel rejectFriendRequest(@PathVariable(value = "requestId", required = false) Integer requestId) {
        return friendRequestService.rejectFriendRequest(friendRequestService.getFriendRequestById(requestId).getId());
    }

    @Operation(summary = "Удаление заявки в друзья")
    @DeleteMapping("/requests/{requestId}")
    @SecurityRequirement(name = "bearerAuth")
    public FriendRequestModel removeRequest(@PathVariable(value = "requestId", required = false) Integer requestId){
        return friendRequestService.remove(friendRequestService.getFriendRequestById(requestId));
    }

    @Operation(summary = "Удаление дружбы")
    @DeleteMapping("/friendships/{friendshipId}")
    @SecurityRequirement(name = "bearerAuth")
    public FriendshipModel removeFriendship(@PathVariable(value = "friendshipId", required = false) Integer friendshipId) {
        return friendshipService.removeFriendship(friendshipService.getFriendshipById(friendshipId));
    }

    @Operation(summary = "Отправка сообщения")
    @PostMapping("/messages")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> sendMessage(@RequestBody @Valid MessageDTO messageDTO, BindingResult bindingResult ){
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }
        return ResponseEntity.status(HttpStatus.OK).body(messageService.sendMessage(converteToMessage(messageDTO)));
    }


    @Operation(summary = "Получение сообщений")
    @GetMapping("/messages")
    @SecurityRequirement(name = "bearerAuth")
    public List<MessageModel> getMessages(@RequestParam(value = "user1Id",required = false) Integer user1Id,
                                          @RequestParam(value = "user2Id",required = false) Integer user2Id) {

        return messageService.getMessages(personDetailsService.findByPersonId(user1Id), personDetailsService.findByPersonId(user2Id));
    }

    private MessageModel converteToMessage(MessageDTO messageDTO) {
            return this.modelMapper.map(messageDTO, MessageModel.class);
    }
}