package com.petersonv.eglucometer.userRegistrationService.entities;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import com.petersonv.eglucometer.userRegistrationService.valueObjects.RandomProvider;

import org.springframework.util.StringUtils;

public class User {
    // Contants
    private static final int MinimumPasswordLength = 4;
    private static final int MaximumPasswordLength = 20;
    private static final int PasswordSaltLength = 32;

    // Properties
    private String email;
    private byte[] pwHash;
    private byte[] pwSalt;

    // Constructors
    public User(String email, String password) {
        if (!User.isEmailValid(email))
            throw new IllegalArgumentException("Invalid email provided.");

        if (!User.isPasswordValid(password))
            throw new IllegalArgumentException(getInvalidPasswordMessage());

        this.email = email;
        this.pwSalt = generatePasswordSalt();
        this.pwHash = getPasswordHash(password, this.pwSalt);
    }

    public String getEmail() {
        return this.email;
    }

    public boolean isPasswordCorrect(String password) {
        final var hashed = getPasswordHash(password, this.pwSalt);
        return Arrays.equals(hashed, pwHash);
    }

    private static boolean isEmailValid(String email) {
        return !StringUtils.isEmpty(email)
            && email.contains("@");
    }

    private static boolean isPasswordValid(String password) {
        return !StringUtils.isEmpty(password)
            && password.length() >= MinimumPasswordLength
            && password.length() <= MaximumPasswordLength;
    }

    private static String getInvalidPasswordMessage() {
        final String template = "The password must have between %d and %d characters.";
        return String.format(template, MinimumPasswordLength, MaximumPasswordLength);
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
}