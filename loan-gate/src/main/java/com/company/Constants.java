package com.company;

import java.io.Serializable;
import java.text.SimpleDateFormat;

/**
 *
 */
public final class Constants implements Serializable {

    public static final String ROOT_PATH = "/api";
    public static final String VERSION = "v1.0";
    public static final String PERSON_ID_KEY_NAME = "person";
    public static final String PERSON_ID_KEY_NAME_JSON = "person_id";

    public static final String DEFAULT_COUNTRY = "lv";

    public static final String DATE_TIME_FORMAT_ISO = "yyyy-MM-dd'T'HH:mm:ss.SSS";
    public static final SimpleDateFormat DATE_TIME_FORMATTER = new SimpleDateFormat(Constants.DATE_TIME_FORMAT_ISO);

}
