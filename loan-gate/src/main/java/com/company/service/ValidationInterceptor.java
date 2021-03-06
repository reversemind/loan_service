package com.company.service;

import com.company.loan.validator.IValidatorChain;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 */
@Slf4j
@Component("validationInterceptor")
public class ValidationInterceptor implements HandlerInterceptor {

    @Autowired
    @Qualifier("httpValidatorChainService")
    IValidatorChain<HttpServletRequest> httpValidatorChainService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.debug("request:{}", request);
        log.debug("request URL:{}", request.getRequestURL());

        boolean isValidRequest = httpValidatorChainService.doValidate(request);
        log.debug("is valid request:{}", isValidRequest);

        return isValidRequest;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
}
