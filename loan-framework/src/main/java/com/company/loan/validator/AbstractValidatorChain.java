package com.company.loan.validator;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Abstract Validator Chain
 */
public abstract class AbstractValidatorChain<T> implements IValidatorChain<T>{

    protected Map<String, IValidator<T>> validatorChainMap = new LinkedHashMap<>();

    /**
     * Add IValidator to the chain
     *
     * @param IValidator -
     * @return - ref to the current AbstractValidatorChain
     */
    @Override
    public synchronized AbstractValidatorChain add(IValidator<T> IValidator) {
        if (IValidator != null) {
            validatorChainMap.put(IValidator.getName(), IValidator);
        }
        return this;
    }

    /**
     * Validate the whole chain
     *
     * @param value - run value through the chain
     * @return - if all validators returns true for the particular value
     */
    @Override
    public boolean doValidate(T value) {
        if (validatorChainMap == null || validatorChainMap.size() <= 0) return true;
        for (IValidator<T> validator : validatorChainMap.values()) {
            if (!validator.isValid(value)) {
                return false;
            }
        }
        return true;
    }
}
