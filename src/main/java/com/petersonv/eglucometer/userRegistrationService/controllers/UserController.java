package com.petersonv.eglucometer.userRegistrationService.controllers;

import javax.validation.Valid;

import com.petersonv.eglucometer.userRegistrationService.entities.User;
import com.petersonv.eglucometer.userRegistrationService.repositories.UserRepository;
import com.petersonv.eglucometer.userRegistrationService.valueObjects.requests.CreateUserRequest;
import com.petersonv.eglucometer.userRegistrationService.valueObjects.responses.CreateUserResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping(UserController.userEndointRelativeRoute)
public class UserController {
    public static final String userEndointRelativeRoute = "/User";

    @Autowired
    public UserRepository userRepo;

    @PostMapping
    public Mono<CreateUserResponse> CreateUser(@Valid @RequestBody CreateUserRequest request) {
        final var user = new User(request);
        return userRepo.insert(user)
            .map((ignore) -> new CreateUserResponse(true, user.getEmail()));
    }
}