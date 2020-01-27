package com.petersonv.eglucometer.userRegistrationService.repositories;

import com.petersonv.eglucometer.userRegistrationService.entities.User;

import reactor.core.publisher.Mono;

public interface UserRepository {
    public Mono<User> getByEmail(String emailAddress);
    public Mono<Void> insert(User user);
}