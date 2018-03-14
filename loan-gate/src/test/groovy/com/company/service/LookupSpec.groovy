package com.company.service

import org.springframework.http.client.HttpComponentsClientHttpRequestFactory
import org.springframework.web.client.RestTemplate
import spock.lang.Ignore
import spock.lang.Specification

/**
 */
class LookupSpec extends Specification {

    @Ignore
    def 'lookup ip to country via external REST request'(){
        setup: 'init'

        HttpComponentsClientHttpRequestFactory httpComponentsClientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory()
        httpComponentsClientHttpRequestFactory.setConnectTimeout(100 * 1000)
        httpComponentsClientHttpRequestFactory.setConnectionRequestTimeout(100 * 1000)

        RestTemplate REST_TEMPLATE = new RestTemplate(httpComponentsClientHttpRequestFactory);

        when: 'make external request'

        Map map = REST_TEMPLATE.getForObject("http://ip-api.com/json/yandex.ru?lang=en", Map.class)

        // map:[as:AS13238 YANDEX LLC, city:Moscow, country:Russia, countryCode:RU, isp:YANDEX LLC, lat:55.7363, lon:37.5884, org:YANDEX LLC, query:87.250.250.242, region:MOW, regionName:Moscow, status:success, timezone:Europe/Moscow, zip:]
        println "map:" + map

        then: 'compare'
        map.get("countryCode") == "RU"
    }
}
