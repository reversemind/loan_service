package com.company.loan.validator;

/**
 *
 */
public interface IValidatorChain<T> {

    IValidatorChain add(IValidator<T> IValidator);

    boolean doValidate(T value);
}
