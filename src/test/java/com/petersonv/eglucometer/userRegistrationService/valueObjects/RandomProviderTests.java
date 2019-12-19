package com.petersonv.eglucometer.userRegistrationService.valueObjects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class RandomProviderTests {
    @Test
    public void getRandomBytes_whenValidLength_shouldReturnCorrectAmountOfbytes() {
        final var lengthsToTest = new int[] {1, 3, 4, 10, 64, 128, 2048};
        for (final var currentLength : lengthsToTest) {
            final var bytes = RandomProvider.getRandomBytes(currentLength);
            assertEquals(currentLength, bytes.length);
        }
    }

    @Test
    public void getRandomBytes_whenNegativeLength_shouldThrow() {
        final var lengthsToTest = new int[] {-1, -3, -4, -10, -64, -128, -2048};
        for (final var currentLength : lengthsToTest) {
            assertThrows(IllegalArgumentException.class, () -> {
                RandomProvider.getRandomBytes(currentLength);
            });
        }
    }
}