package com.company.service.validator;

import com.company.loan.validator.IValidator;
import com.company.api.ICountryNameByIp;
import com.company.service.RequestsCounterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

import static com.company.Constants.DEFAULT_COUNTRY;

/**
 *
 */
@Slf4j
@ConfigurationProperties
@Service("requestsPerCountryValidatorService")
public class RequestsPerCountryValidatorService implements IValidator<HttpServletRequest> {

    @Value("${loan.gate.requestLimitPerCountry:1}")
    private Long requestLimitPerCountry;

    @Autowired
    @Qualifier(value = "countryNameLookupService")
    ICountryNameByIp countryNameLookupService;

    @Autowired
    RequestsCounterService requestsCounterService;

    @Override
    public String getName() {
        return getClass().getCanonicalName();
    }

    @Override
    public boolean isValid(HttpServletRequest value) {
        if (value == null) return true;

        String remoteAddress = value.getRemoteAddr();
        log.debug("remoteAddress:{}", remoteAddress);

        String countryCode = countryNameLookupService.getByIp(remoteAddress);
        Long hits = requestsCounterService.hit(countryCode);

        return hits <= requestLimitPerCountry;
    }
}
