package ru.timur.socialmediaapi.SocialMediaApi.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.timur.socialmediaapi.SocialMediaApi.api.dto.PostDTO;
import ru.timur.socialmediaapi.SocialMediaApi.api.dto.PostUpdateDTO;
import ru.timur.socialmediaapi.SocialMediaApi.config.security.PersonDetails;
import ru.timur.socialmediaapi.SocialMediaApi.core.model.PersonModel;
import ru.timur.socialmediaapi.SocialMediaApi.core.model.PostModel;
import ru.timur.socialmediaapi.SocialMediaApi.core.service.PostService;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostsController {

    private final PostService postService;
    private final ModelMapper modelMapper;

    @Operation(summary = "Создание поста")
    @PostMapping()
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> create(@RequestBody @Valid PostDTO postDTO,
                                    BindingResult bindingResult){

        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();

        PersonModel person = personDetails.getPerson();
        PostModel post = converteToPost(postDTO);
        post.setPerson(person.getId());

        return ResponseEntity.status(HttpStatus.OK).body(postService.create(post));
    }

    @Operation(summary = "Вывод всех постов")
    @GetMapping()
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> showAll(){
        return ResponseEntity.status(HttpStatus.OK).body(postService.showAll());
    }

    @Operation(summary = "Изменение поста")
    @PutMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> update(@PathVariable("id") int id, @RequestBody @Valid PostUpdateDTO postUpdateDTO,
                       BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }
        if (postUpdateDTO.getLinkForImage() == null && postUpdateDTO.getHeader() == null && postUpdateDTO.getText() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You didn't fill in one field");
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        PersonModel person = personDetails.getPerson();

        PostModel postToBeUpdated = postService.findById(id);
        if (postUpdateDTO.getHeader() != null)
            postToBeUpdated.setHeader(postUpdateDTO.getHeader());
        if (postUpdateDTO.getText() != null)
            postToBeUpdated.setText(postUpdateDTO.getText());
        if (postUpdateDTO.getLinkForImage() != null)
            postToBeUpdated.setLinkForImage(postUpdateDTO.getLinkForImage());
        if(person.getId() == postToBeUpdated.getPerson()){
            return ResponseEntity.status(HttpStatus.OK).body(postService.update(postToBeUpdated));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You cannot edit this post because you are not the owner");
        }
    }

    @Operation(summary = "Удаление поста")
    @DeleteMapping("/delete/{id}")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> delete(@PathVariable int id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        PersonModel person = personDetails.getPerson();
        PostModel postToBeDeleted = postService.findById(id);
        if (person.getId() == postToBeDeleted.getPerson()){
            postService.delete(postToBeDeleted);
            return ResponseEntity.status(HttpStatus.OK).body("OK");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You cannot delete this post because you are not the owner");
    }

    private PostModel converteToPost(PostDTO postDTO) {
        return this.modelMapper.map(postDTO, PostModel.class);
    }

}
