package com.davidbuhler.plaidsuit.client.util;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.groups.Default;
import java.util.ArrayList;
import java.util.Set;

public class ValidationUtil {
    public static <T> ValidationResult getValidationState(final T value)
    {
        final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        final Set<ConstraintViolation<T>> violations = validator.validate(value, Default.class);
        final ArrayList<String> errors = new ArrayList<String>();
        for (final ConstraintViolation<T> c : violations)
        {
            errors.add(c.getMessage());
        }
        final ValidationResult validationResult = new ValidationResult(errors);
        return validationResult;
    }
}
