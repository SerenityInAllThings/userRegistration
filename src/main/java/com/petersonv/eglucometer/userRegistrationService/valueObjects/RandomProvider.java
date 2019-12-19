package com.petersonv.eglucometer.userRegistrationService.valueObjects;

import java.security.SecureRandom;

public class RandomProvider {
    // TODO: get the seed from outside the code
    private static final byte[] seed = "minhaSeedQualquer".getBytes();
    private static final SecureRandom secureRandom = new SecureRandom(seed);

    public static byte[] getRandomBytes(int length) {
        if (length <= 0)
            throw new IllegalArgumentException("Length needs to be a positive number");

        var randomBytes = new byte[length];
        secureRandom.nextBytes(randomBytes);
        return randomBytes;
    }
}