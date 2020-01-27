package com.petersonv.eglucometer.userRegistrationService;

import javax.validation.Validation;
import javax.validation.Validator;

public class TestHelper {
    public final static TestHelper singleton = new TestHelper();

    private TestHelper() {
    }

    public Validator getDataValidor() {
        var factory = Validation.buildDefaultValidatorFactory();
        var validator = factory.getValidator();
        return validator;
    }

}