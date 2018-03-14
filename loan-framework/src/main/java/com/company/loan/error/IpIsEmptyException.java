package com.company.loan.error;

/**
 */
public class IpIsEmptyException extends Exception {

    public IpIsEmptyException(String serviceName) {
        super("Unable to execute external lookup for empty for service:" + serviceName);
    }
}
