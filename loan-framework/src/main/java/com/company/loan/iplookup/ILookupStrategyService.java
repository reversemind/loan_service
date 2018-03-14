package com.company.loan.iplookup;

import com.company.loan.error.IpIsEmptyException;

import java.util.Map;

/**
 * IP lookup service
 */
public interface ILookupStrategyService {

    String getServiceName();

    String getServiceUrl(String ip) throws IpIsEmptyException;

    String parseCountryCode(Map map);
}
