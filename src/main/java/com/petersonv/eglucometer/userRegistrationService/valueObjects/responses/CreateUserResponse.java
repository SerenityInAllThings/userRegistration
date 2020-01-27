package com.petersonv.eglucometer.userRegistrationService.valueObjects.responses;

public class CreateUserResponse {
    public final boolean created;
    public final String emailAddress;

    public CreateUserResponse(boolean created, String emailAddress) {
        this.created = created;
        this.emailAddress = emailAddress;
    }
}