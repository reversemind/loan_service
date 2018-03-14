package com.company.utils;

import static com.company.Constants.PERSON_ID_KEY_NAME;

/**
 *
 */
public final class StringUtils {

    /**
     * Extract person id from URI
     * implemented a simplified parsing case - just for test task
     *
     * @param uri -
     * @return - personId
     */
    public static Long parsePersonId(String uri) {
        if (uri == null || uri.trim().length() == 0) return -1L;
        Long personId = -1L;
        try {
            // simplified parsing case - just for test task
            personId = Long.valueOf(uri.substring(uri.indexOf(PERSON_ID_KEY_NAME) + PERSON_ID_KEY_NAME.length() + 1, uri.length()));
        } catch (Exception ignore) {
        }
        return personId;
    }

}
