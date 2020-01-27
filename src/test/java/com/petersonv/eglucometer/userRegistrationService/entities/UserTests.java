package com.petersonv.eglucometer.userRegistrationService.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import javax.validation.ConstraintViolationException;

import com.petersonv.eglucometer.userRegistrationService.entities.User;
import com.petersonv.eglucometer.userRegistrationService.valueObjects.requests.CreateUserRequest;

public class UserTests {

    private CreateUserRequest getCreateUserRequestSample() {
        final var sample = new CreateUserRequest();
        sample.email = "valid@domain.com";
        sample.birthdate = LocalDate.parse("1997-01-25");
        sample.firstName = "Pet";
        sample.lastName = "Gama";
        sample.password =  "mySafestP44sEv3r";
        return sample;
    }

    @Test
    public void constructor_whenRequestIsNull_shouldThrow() {
        assertThrows(IllegalArgumentException.class, () -> new User(null));
    }

    @Test 
    public void constructor_whenRequestHasConstraintViolations_shouldThrow() {
        final var mockedRequest = getCreateUserRequestSample();
        mockedRequest.email = null; // This is required to get the constrait error
        assertThrows(ConstraintViolationException.class, () -> new User(mockedRequest));
    }

    @Test
    public void isPasswordCorrect_whenPasswordCorrect_shouldReturnTrue() {
        final var createRequest = getCreateUserRequestSample();
        final var user = new User(createRequest);
        assertTrue(user.isPasswordCorrect(createRequest.password));
    }

    @Test
    public void isPasswordCorrect_whenPasswordIncorrect_shouldReturnTrue() {
        final var createRequest = getCreateUserRequestSample();
        final var user = new User(createRequest);
        assertFalse(user.isPasswordCorrect(createRequest.password + "someString"));
    }
}