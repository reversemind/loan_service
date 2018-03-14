package com.company.service.iplookup;

import com.company.loan.error.IpIsEmptyException;
import com.company.loan.iplookup.ILookupStrategyService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;

import static com.company.Constants.DEFAULT_COUNTRY;

/**
 * Use freegeoip.net/{format}/{IP_or_hostname} as lookup service
 */
@Service("freeGeoIpLookupService")
public class FreeGeoIpLookupService implements ILookupStrategyService {

    /**
     * External service name
     *
     * @return - name
     */
    @Override
    public String getServiceName() {
        return "freegeoip.net";
    }

    /**
     * Generate GET HTTP request specific for the particular external REST service
     *
     * @param ip - lookup IP
     * @return - URL
     * @throws IpIsEmptyException - if IP is EMPTY
     */
    @Override
    public String getServiceUrl(String ip) throws IpIsEmptyException {
        if (StringUtils.isEmpty(ip)) throw new IpIsEmptyException(this.getServiceName());
        return "http://freegeoip.net/json/" + ip;
    }

    /**
     * Parse response and find countryCode
     *
     * @param map - response from external lookup service
     * @return - countryCode or DEFAULT_COUNTRY
     */
    @Override
    public String parseCountryCode(Map map) {
        if (map == null || map.size() == 0) return DEFAULT_COUNTRY;

        Object _object = map.get("country_code");
        if (_object != null) {
            return _object.toString().toLowerCase();
        }

        return DEFAULT_COUNTRY;
    }
}
