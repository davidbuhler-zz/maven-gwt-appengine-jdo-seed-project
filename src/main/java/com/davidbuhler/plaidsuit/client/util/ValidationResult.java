package com.davidbuhler.plaidsuit.client.util;

import java.util.ArrayList;

public class ValidationResult
{
    private final ArrayList<String>	errors;

    public ValidationResult(final ArrayList<String> value)
    {
        errors = value;
    }

    public ArrayList<String> getErrors()
    {
        return errors;
    }

    public boolean isValid()
    {
        return errors != null && errors.size() == 0;
    }
}