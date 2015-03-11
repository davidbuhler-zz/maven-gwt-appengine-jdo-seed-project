package com.davidbuhler.plaidsuit.shared.validation;

import com.davidbuhler.plaidsuit.shared.dto.ContactDTO;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.validation.client.AbstractGwtValidatorFactory;
import com.google.gwt.validation.client.GwtValidation;
import com.google.gwt.validation.client.impl.AbstractGwtValidator;

import javax.validation.Validator;


public final class ValidatorFactory extends AbstractGwtValidatorFactory {
    @GwtValidation({
            ContactDTO.class
    })
    public interface GwtValidator extends Validator {
    }

    @Override
    public AbstractGwtValidator createValidator() {
        return GWT.create(GwtValidator.class);
    }
}