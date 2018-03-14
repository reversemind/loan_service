package com.company.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 */
@Slf4j
@Service("blackListPersonService")
public class BlackListPersonService {

    // this is basically just for test
    // real implementation should do it via any storage like SQL or NoSQL DB or any distributed cache like Redis, Hazelcast and etc.
    public static final Map<Long, Boolean> BLACK_LIST_PERSON_MAP = new ConcurrentHashMap<>();

    // just initialize with couple of values
    static {
        BLACK_LIST_PERSON_MAP.put(10L, true);
        BLACK_LIST_PERSON_MAP.put(11L, true);
    }

    /**
     * Lookup in black list
     *
     * @param personId -
     * @return - true or false
     */
    public boolean isInBlackList(Long personId) {
        return personId != null && BLACK_LIST_PERSON_MAP.get(personId) != null;
    }

}
