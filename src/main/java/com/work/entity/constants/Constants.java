package com.work.entity.constants;

public class Constants {
    public static final Integer ONE = 1;
    public static final Integer ZERO = 0;
    public static final Integer TWO = 2;
    public static final Integer LENGTH_10 = 10;
    public static final Integer LENGTH_15 = 15;
    public static final Integer LENGTH_20 = 20;
    public static final Integer LENGTH_30 = 30;

    public static final Long MB_SIZE = 1024 * 1024L;

    public static final String REGEX_PASSWORD = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d]{8,}$";

    public static final Integer REDIS_KEY_EXPIRES_ONE_SECONDS = 1000;
    public static final Integer REDIS_KEY_EXPIRES_ONE_MIN = 60000;
    public static final Integer REDIS_KEY_EXPIRES_ONE_DAY = REDIS_KEY_EXPIRES_ONE_MIN * 60 * 24;
    public static final Integer TIME_SECONDS_DAY = REDIS_KEY_EXPIRES_ONE_DAY / 1000;
    public static final Integer UPDATE_NICK_NAME_COIN = 5;

    public static final String REDIS_KEY_PREFIX = "web:";
    public static final String REDIS_KEY_CHECK_CODE = REDIS_KEY_PREFIX + "checkCode:";



}
