package com.company.service.validator;

import com.company.loan.validator.AbstractValidatorChain;
import com.company.loan.validator.IValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

/**
 * Http Validator Chain service
 */
@Service("httpValidatorChainService")
public class HttpValidatorChainService extends AbstractValidatorChain<HttpServletRequest> {

    @Autowired
    @Qualifier("blackListValidatorService")
    IValidator<HttpServletRequest> blackListValidatorService;

    @Autowired
    @Qualifier("requestsPerCountryValidatorService")
    IValidator<HttpServletRequest> requestsPerCountryValidatorService;

    @PostConstruct
    public void init(){
        this.add(blackListValidatorService);
        this.add(requestsPerCountryValidatorService);
    }
}
