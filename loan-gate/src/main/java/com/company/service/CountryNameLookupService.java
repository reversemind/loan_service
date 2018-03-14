package com.company.service;

import com.company.api.ICountryNameByIp;
import com.company.loan.iplookup.ILookupStrategyService;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

import static com.company.Constants.DEFAULT_COUNTRY;

/**
 * Lookup country name by ip
 */
@Slf4j
@Service("countryNameLookupService")
public class CountryNameLookupService implements ICountryNameByIp {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    @Qualifier(value = "ipApiComLookupService")
    ILookupStrategyService ipLookup;

    /**
     * Lookup country code by ip
     * @param ip - real ip or host name of remote
     * @return - country code
     */
    @Override
    @Cacheable(value = "countryCodeByIp")
    public String getByIp(String ip) {
        log.debug("Going to look up ip:{}", ip);
        if (StringUtils.isEmpty(ip)) return DEFAULT_COUNTRY;

        String country = DEFAULT_COUNTRY;
        try {
            Map map = restTemplate.getForObject(ipLookup.getServiceUrl(ip), Map.class);
            log.debug("response map:{}", map);
            country = ipLookup.parseCountryCode(map);
        } catch (Exception ex) {
            log.error("Unable to lookup country for ip:{}", ip, ex);
        }
        return country;
    }

    /**
     *
     * @param request - HttpServletRequest
     * @return - valid country code
     */
    public String getByIp(HttpServletRequest request){
        if(request == null) return DEFAULT_COUNTRY;

        String remoteAddress = request.getRemoteAddr();
        log.debug("remoteAddress:{}", remoteAddress);

        return this.getByIp(remoteAddress);
    }

}
