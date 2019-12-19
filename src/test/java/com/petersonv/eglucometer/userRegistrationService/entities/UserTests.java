package com.petersonv.eglucometer.userRegistrationService.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.petersonv.eglucometer.userRegistrationService.entities.User;

public class UserTests {
    @Test
    public void constructor_whenCalled_shouldNotThrow() {
        new User("some@email.com", "somePassword");
    }

    @Test
    public void constructor_whenEmailEmpty_shouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> {
            new User("", "somepassword");
        });
    }

    @Test
    public void constructor_whenEmailNull_shouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> {
            new User(null, "somepassword");
        });
    }

    @Test
    public void constructor_whenEmailDoesNotContainAt_shouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> {
            new User("TextWithoutAnAt", "somepassword");
        });
    }

    @Test
    public void constructor_whenPasswordEmpty_shouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> {
            new User("some@email.com", "");
        });
    }

    @Test
    public void constructor_whenPasswordNull_shouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> {
            new User("some@email.com", null);
        });
    }

    @Test
    public void constructor_whenPasswordTooSmall_shouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> {
            new User("some@email.com", "aaa");
        });
    }

    @Test
    public void constructor_whenPasswordTooBig_shouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> {
            new User("some@email.com", "123456789012345678901");
        });
    }

    @Test
    public void constructor_whenGetEmail_shouldReturnSameAsProvided() {
        final var email = "some@mail.com";
        final var user = new User(email, "someRandomPasswd@#");
        assertEquals(email, user.getEmail());
    }

    @Test
    public void isPasswordCorrect_whenPasswordCorrect_shouldReturnTrue() {
        final var passwd = "someRandomPasswd@#";
        final var user = new User("some@mail.com", passwd);
        assertTrue(user.isPasswordCorrect(passwd));
    }

    @Test
    public void isPasswordCorrect_whenPasswordIncorrect_shouldReturnTrue() {
        final var passwd = "someRandomPasswd@#";
        final var user = new User("some@mail.com", passwd);
        assertFalse(user.isPasswordCorrect(passwd + "someString"));
    }
}