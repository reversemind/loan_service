package com.company.service;

import com.company.model.RequestsPerCountry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_SINGLETON;

/**
 * Count requests by country code - should be a single per JVM
 */
@Slf4j
@Scope(value = SCOPE_SINGLETON) // just define it explicitly once again
@Service("requestsCounterService")
public class RequestsCounterService {

    @Value(value = "${loan.gate.requestPeriodCounter:1000}")
    private Long requestPeriodCounter;

    protected final Map<String, RequestsPerCountry> counterMap = new ConcurrentHashMap<>(512);

    /**
     * Get number of requests for period using countryCode
     *
     * @param countryCode -
     * @return - number of request per period of time (ms)
     */
    public Long getRequestsByCountryCode(String countryCode) {
        if (StringUtils.isEmpty(countryCode)) return 0L;

        RequestsPerCountry requestsPerCountry = counterMap.get(countryCode);
        if (requestsPerCountry == null) return 0L;
        return requestsPerCountry.getHitsPerPeriod().get();
    }

    /**
     * Initialize new Country for requests per time
     *
     * @param countryCode - country code like: "lv"
     */
    protected void initializeNewCountry(String countryCode) {
        RequestsPerCountry requestsPerCountry = new RequestsPerCountry(countryCode, requestPeriodCounter);
        counterMap.putIfAbsent(countryCode, requestsPerCountry);
    }

    /**
     * Hit per countryCode
     *
     * @param countryCode -
     * @return - number of hits
     */
    public Long hit(String countryCode) {
        if (StringUtils.isEmpty(countryCode)) return 0L;

        log.debug("Hit request for countryCode:{}", countryCode);
        RequestsPerCountry requestsPerCountry = counterMap.get(countryCode);

        if (requestsPerCountry == null) {
            this.initializeNewCountry(countryCode);
        } else {
            requestsPerCountry.hit();
            counterMap.put(countryCode, requestsPerCountry);
        }

        return this.getRequestsByCountryCode(countryCode);
    }
}
