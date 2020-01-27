package com.petersonv.eglucometer.userRegistrationService.entities;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.UUID;

import javax.validation.ConstraintViolationException;

import com.petersonv.eglucometer.userRegistrationService.valueObjects.RandomProvider;
import com.petersonv.eglucometer.userRegistrationService.valueObjects.requests.CreateUserRequest;

import org.msgpack.annotation.Message;

@Message
public class User {
    // Contants
    public static final int MinPasswordLength = 4;
    public static final int MaxPasswordLength = 20;
    public static final int MinNameLength = 2;
    public static final int MaxNameLength = 64;
    private static final int PasswordSaltLength = 32;

    // Properties
    private String id;
    private String email;
    private String firstname;
    private String lastName;
    private LocalDate birthdate;
    private byte[] pwHash;
    private byte[] pwSalt;

    public User(CreateUserRequest request) {
        if (request == null)
            throw new IllegalArgumentException("Create user request cannot be null.");

        final var constraintViolations = request.getConstraintViolations();
        if (constraintViolations.size() != 0)
            throw new ConstraintViolationException(constraintViolations);

        this.id = generateId();
        this.email = request.email;
        this.firstname = request.firstName;
        this.lastName = request.lastName;
        this.pwSalt = generatePasswordSalt();
        this.pwHash = getPasswordHash(request.password, this.pwSalt);
    }

    public String getEmail() {
        return this.email;
    }

    public String getFirstName() {
        return this.firstname;
    }

    public String getLastName() {
        return this.lastName;
    }

    public LocalDate getBirthDate() {
        return this.birthdate;
    }

    public boolean isPasswordCorrect(String password) {
        final var hashed = getPasswordHash(password, this.pwSalt);
        return Arrays.equals(hashed, pwHash);
    }

    private static byte[] generatePasswordSalt() {
        return RandomProvider.getRandomBytes(PasswordSaltLength);
    }

    private static byte[] getPasswordHash(String password, byte[] salt) {
        try {
            final var md = MessageDigest.getInstance("SHA-512");
            md.update(salt);
            final byte[] hashedPassword = md.digest(password.getBytes(StandardCharsets.UTF_8));
            return hashedPassword;
        }
        catch (NoSuchAlgorithmException ex) {
            throw new RuntimeException("Algorithm not found", ex);
        }
    }

    private static String generateId() {
        return UUID.randomUUID().toString();
    }
}