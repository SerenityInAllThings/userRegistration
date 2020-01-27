package com.petersonv.eglucometer.userRegistrationService.valueObjects.requests;

import java.time.LocalDate;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import com.petersonv.eglucometer.userRegistrationService.entities.User;

public class CreateUserRequest {
    @Email
    @NotNull
    public String email;

    @NotNull
    @Size(min = User.MinNameLength, max = User.MaxNameLength)
    public String firstName;

    @NotNull
    @Size(min = User.MinNameLength, max = User.MaxNameLength)
    public String lastName;

    @Past
    public LocalDate birthdate;

    @Size(min = User.MinPasswordLength, max = User.MaxPasswordLength)
    public String password;

    public Set<ConstraintViolation<CreateUserRequest>> getConstraintViolations() {
        final var factory = Validation.buildDefaultValidatorFactory();
        final var validator = factory.getValidator();
        return validator.validate(this);
    }
}