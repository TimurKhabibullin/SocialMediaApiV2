package ru.timur.socialmediaapi.SocialMediaApi.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
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
import ru.timur.socialmediaapi.SocialMediaApi.core.model.PostModel;
import ru.timur.socialmediaapi.SocialMediaApi.core.service.PostService;
import java.util.HashMap;
import java.util.List;
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
    public PostModel create(@RequestBody @Valid PostDTO postDTO, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
        }
        return postService.create(converteToPost(postDTO), SecurityContextHolder.getContext().getAuthentication());
    }

    @Operation(summary = "Вывод всех постов")
    @GetMapping()
    @SecurityRequirement(name = "bearerAuth")
    public List<PostModel> showAll(){
        return postService.showAll();
    }

    @Operation(summary = "Изменение поста")
    @PutMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    public PostModel update(@PathVariable("id") int id, @RequestBody @Valid PostUpdateDTO postUpdateDTO,
                       BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
        }
        return postService.update(converteToPost(postUpdateDTO), id);

    }

    @Operation(summary = "Удаление поста")
    @DeleteMapping("/delete/{id}")
    @SecurityRequirement(name = "bearerAuth")
    public PostModel delete(@PathVariable int id){
        return postService.delete(postService.findById(id));
    }

    private PostModel converteToPost(PostDTO postDTO) {
        return this.modelMapper.map(postDTO, PostModel.class);
    }

    private PostModel converteToPost(PostUpdateDTO postDTO) {
        return this.modelMapper.map(postDTO, PostModel.class);
    }
}
