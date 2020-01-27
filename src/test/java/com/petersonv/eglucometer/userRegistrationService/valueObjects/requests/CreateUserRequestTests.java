package com.petersonv.eglucometer.userRegistrationService.valueObjects.requests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import com.petersonv.eglucometer.userRegistrationService.TestHelper;

import org.junit.jupiter.api.Test;

public class CreateUserRequestTests {
    @Test
    public void constructor_isParameterlessVersionPublic() {
        new CreateUserRequest();
    }

    @Test
    public void dataValidationEmail_whenInvalid_shouldReturnError() {
        final var invalidEmails = new String[] { 
            "simpleText", "12345", "noDomain@", "@noUser.com", "#@%^%#$@#$@#.com",
            "Joe Smith <email@example.com>", "email.example.com", "email@example@example.com", ".email@example.com",
            "あいう えお@example.com", "email@example.com (Joe Smith)", "Abc..123@example.com" 
        };
        var validator = TestHelper.singleton.getDataValidor();
        final var createUserRequest = getValidSample();

        for (final var currentEmail : invalidEmails) {
            createUserRequest.email = currentEmail;
            final var errors = validator.validate(createUserRequest);
            assertEquals(1, errors.size());
        }
    }

    @Test
    public void dataValidationEmail_whenNull_shouldReturnError() {
        var validator = TestHelper.singleton.getDataValidor();
        final var createUserRequest = getValidSample();
        createUserRequest.email = null;
        final var errors = validator.validate(createUserRequest);
        assertEquals(1, errors.size());
    }

    @Test
    public void dataValidationEmail_whenValid_shouldNotReturnError() {
        final var validEmails = new String[] { 
            "email@example.com", "firstname.lastname@example.com", "email@subdomain.example.com",
            "firstname+lastname@example.com", "email@123.123.123.123", "email@[123.123.123.123]",
            "\"email\"@example.com", "1234567890@example.com", "email@example-one.com",
            "_______@example.com", "email@example.name", "email@example.museum",
            "email@example.co.jp", "firstname-lastname@example.com"
        };
        var validator = TestHelper.singleton.getDataValidor();
        final var createUserRequest = getValidSample();

        for (final var currentEmail : validEmails) {
            createUserRequest.email = currentEmail;
            final var errors = validator.validate(createUserRequest);
            assertEquals(0, errors.size());
        }
    }

    @Test
    public void dataValidationFirstName_whenToLittle_shouldReturnError() {
        final var invalidNames = new String[] { 
            "a", "b", "1"
        };
        var validator = TestHelper.singleton.getDataValidor();
        final var createUserRequest = getValidSample();

        for (final var currentName : invalidNames) {
            createUserRequest.firstName = currentName;
            final var errors = validator.validate(createUserRequest);
            assertEquals(1, errors.size());
        }
    }

    @Test
    public void dataValidationFirstName_whenToBig_shouldReturnError() {
        final var invalidNames = new String[] { 
            "7Qc8o4lnykYo9I7eNZ4kfGSLViV1yBlqMiLopEl4ErUgejhTAZEbl0AWvz6xAQhId",
            "s9VSXaATZKL6u8u4SWSCbevQJwbVw3d3zfJDXitBCxQ8VNwLygns1RWugrJkgQXAGyj82TvjOtmNpUq4",
        };
        var validator = TestHelper.singleton.getDataValidor();
        final var createUserRequest = getValidSample();

        for (final var currentName : invalidNames) {
            createUserRequest.firstName = currentName;
            final var errors = validator.validate(createUserRequest);
            assertEquals(1, errors.size());
        }
    }

    @Test
    public void dataValidationFirstName_whenRightLength_shouldNotReturnError() {
        final var invalidNames = new String[] { 
            "peterson victor",
            "gabriela",
            "João",
        };
        var validator = TestHelper.singleton.getDataValidor();
        final var createUserRequest = getValidSample();

        for (final var currentName : invalidNames) {
            createUserRequest.firstName = currentName;
            final var errors = validator.validate(createUserRequest);
            assertEquals(0, errors.size());
        }
    }

    @Test
    public void dataValidationLastName_whenToLittle_shouldReturnError() {
        final var invalidNames = new String[] { 
            "a", "b", "1"
        };
        var validator = TestHelper.singleton.getDataValidor();
        final var createUserRequest = getValidSample();

        for (final var currentName : invalidNames) {
            createUserRequest.lastName = currentName;
            final var errors = validator.validate(createUserRequest);
            assertEquals(1, errors.size());
        }
    }

    @Test
    public void dataValidationLastName_whenToBig_shouldReturnError() {
        final var invalidNames = new String[] { 
            "7Qc8o4lnykYo9I7eNZ4kfGSLViV1yBlqMiLopEl4ErUgejhTAZEbl0AWvz6xAQhId",
            "s9VSXaATZKL6u8u4SWSCbevQJwbVw3d3zfJDXitBCxQ8VNwLygns1RWugrJkgQXAGyj82TvjOtmNpUq4",
        };
        var validator = TestHelper.singleton.getDataValidor();
        final var createUserRequest = getValidSample();

        for (final var currentName : invalidNames) {
            createUserRequest.lastName = currentName;
            final var errors = validator.validate(createUserRequest);
            assertEquals(1, errors.size());
        }
    }

    @Test
    public void dataValidationLastName_whenRightLength_shouldNotReturnError() {
        final var invalidNames = new String[] { 
            "peterson victor",
            "gabriela",
            "João",
        };
        var validator = TestHelper.singleton.getDataValidor();
        final var createUserRequest = getValidSample();

        for (final var currentName : invalidNames) {
            createUserRequest.lastName = currentName;
            final var errors = validator.validate(createUserRequest);
            assertEquals(0, errors.size());
        }
    }

    @Test
    public void dataValidationBirthDate_whenInFuture_shouldReturnError() {
        final var today = LocalDate.now();
        final var datesInFuture = new LocalDate[] { 
            today.plusDays(1),
            today.plusMonths(1),
            today.plusYears(1),
            today.plusWeeks(1),
        };
        var validator = TestHelper.singleton.getDataValidor();
        final var createUserRequest = getValidSample();

        for (final var currentDate : datesInFuture) {
            createUserRequest.birthdate = currentDate;
            final var errors = validator.validate(createUserRequest);
            assertEquals(1, errors.size());
        }
    }

    @Test
    public void dataValidationBirthDate_whenInPast_shouldNotReturnError() {
        final var today = LocalDate.now();
        final var datesInFuture = new LocalDate[] { 
            today.minusDays(1),
            today.minusMonths(1),
            today.minusYears(1),
            today.minusWeeks(1),
        };
        var validator = TestHelper.singleton.getDataValidor();
        final var createUserRequest = getValidSample();

        for (final var currentDate : datesInFuture) {
            createUserRequest.birthdate = currentDate;
            final var errors = validator.validate(createUserRequest);
            assertEquals(0, errors.size());
        }
    }

    @Test
    public void dataValidationPassword_whenTooLittle_shouldReturnError() {
        final var tooShortPasswords = new String[] {
            "1", "12", "123", "a", "b", "c"
        };
        var validator = TestHelper.singleton.getDataValidor();
        final var createUserRequest = getValidSample();

        for (final var currentPassword : tooShortPasswords) {
            createUserRequest.password = currentPassword;
            final var errors = validator.validate(createUserRequest);
            assertEquals(1, errors.size());
        }
    }

    @Test
    public void dataValidationPassword_whenTooBig_shouldReturnError() {
        final var tooShortPasswords = new String[] {
            "123456789012345678901", "abcdefghijklmnopqrstu"
        };
        var validator = TestHelper.singleton.getDataValidor();
        final var createUserRequest = getValidSample();

        for (final var currentPassword : tooShortPasswords) {
            createUserRequest.password = currentPassword;
            final var errors = validator.validate(createUserRequest);
            assertEquals(1, errors.size());
        }
    }

    @Test
    public void dataValidationPassword_whenRightLength_shouldNotReturnError() {
        final var tooShortPasswords = new String[] {
            "mySamplePassrod123", "another!#@Pass", "Another one"
        };
        var validator = TestHelper.singleton.getDataValidor();
        final var createUserRequest = getValidSample();

        for (final var currentPassword : tooShortPasswords) {
            createUserRequest.password = currentPassword;
            final var errors = validator.validate(createUserRequest);
            assertEquals(0, errors.size());
        }
    }

    private CreateUserRequest getValidSample() {
        final var sample = new CreateUserRequest();
        sample.email = "sampleEmail@sampleDomain.org";
        sample.firstName = "john";
        sample.lastName = "smith";
        sample.birthdate = LocalDate.parse("1997-01-25");
        sample.password = "mySamplePw123#";
        return sample;
    }
}