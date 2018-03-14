package com.company.loan.validator;

/**
 */
public interface IValidator<T> {

    /**
     * @return IValidator unique name
     */
    String getName();

    /**
     * Validate a value
     *
     * @param value - Value to validate
     * @return - true or false
     */
    boolean isValid(T value);
}
