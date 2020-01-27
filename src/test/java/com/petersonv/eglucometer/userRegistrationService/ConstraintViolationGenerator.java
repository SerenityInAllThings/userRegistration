package com.petersonv.eglucometer.userRegistrationService;

import java.util.Set;

import javax.validation.ConstraintViolation;

public class ConstraintViolationGenerator {
    public final static ConstraintViolationGenerator singleton = new ConstraintViolationGenerator();

    private ConstraintViolationGenerator() {

    }

    public <T> Set<ConstraintViolation<T>> getConstratintViolations(T bean, String beanProperty) {
        final var validator = TestHelper.singleton.getDataValidor();
        return validator.validateValue((Class<T>)bean.getClass(), beanProperty, null);
    }
}