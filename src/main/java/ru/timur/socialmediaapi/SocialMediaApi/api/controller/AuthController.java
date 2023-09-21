package ru.timur.socialmediaapi.SocialMediaApi.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.timur.socialmediaapi.SocialMediaApi.api.dto.PersonDTO;
import ru.timur.socialmediaapi.SocialMediaApi.api.dto.AuthenticationDTO;
import ru.timur.socialmediaapi.SocialMediaApi.core.model.PersonModel;
import ru.timur.socialmediaapi.SocialMediaApi.core.service.RegistrationService;
import ru.timur.socialmediaapi.SocialMediaApi.config.security.JWTUtil;
import ru.timur.socialmediaapi.SocialMediaApi.core.utils.PersonValidator;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final RegistrationService registrationService;
    private final PersonValidator personValidator;
    private final JWTUtil jwtUtil;
    private final ModelMapper modelMapper;
    private final AuthenticationManager authenticationManager;


    @Operation(summary = "Регистрация")
    @PostMapping("/registration")
    public ResponseEntity<?> performRegistration(@RequestBody @Valid PersonDTO personDTO,
                                                   BindingResult bindingResult) {
        PersonModel personModel = convertToPerson(personDTO);

        personValidator.validate(personModel, bindingResult);

        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }

        registrationService.register(personModel);
        personModel.setRole("ROLE_USER");
        String token = jwtUtil.generateToken(personModel.getUsername());
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("jwt-token", token));
    }

    @Operation(summary = "Авторизация")
    @PostMapping("/login")
    public ResponseEntity<?> performLogin(@RequestBody AuthenticationDTO authenticationDTO) {
        UsernamePasswordAuthenticationToken authInputToken =
                new UsernamePasswordAuthenticationToken(authenticationDTO.getUsername(),
                        authenticationDTO.getPassword());

        try {
            authenticationManager.authenticate(authInputToken);
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", "Incorrect credentials!"));
        }

        String token = jwtUtil.generateToken(authenticationDTO.getUsername());
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("jwt-token", token));
    }

    public PersonModel convertToPerson(PersonDTO personDTO) {
        return this.modelMapper.map(personDTO, PersonModel.class);
    }
}