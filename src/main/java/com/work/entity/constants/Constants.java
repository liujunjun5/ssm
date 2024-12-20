package com.work.entity.constants;

public class Constants {
    public static final Integer ONE = 1;
    public static final Integer ZERO = 0;
    public static final Integer TWO = 2;
    public static final Integer THREE = 3;
    public static final Integer LENGTH_10 = 10;
    public static final Integer LENGTH_15 = 15;
    public static final Integer LENGTH_20 = 20;
    public static final Integer LENGTH_30 = 30;

    public static final Long MB_SIZE = 1024 * 1024L;

    public static final String REGEX_PASSWORD = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d]{8,}$";
    public static final String REGEX_EMAIL = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z]{2,}$";

    public static final Integer REDIS_KEY_EXPIRES_ONE_SECONDS = 1000;
    public static final Integer REDIS_KEY_EXPIRES_ONE_MIN = 60000;
    public static final Integer REDIS_KEY_EXPIRES_ONE_DAY = REDIS_KEY_EXPIRES_ONE_MIN * 60 * 24;
    public static final Integer TIME_SECONDS_DAY = REDIS_KEY_EXPIRES_ONE_DAY / 1000;
    public static final Integer UPDATE_NICK_NAME_COIN = 5;

    public static final String REDIS_KEY_PREFIX = "web:";
    public static final String REDIS_KEY_CHECK_CODE = REDIS_KEY_PREFIX + "checkCode:";

    /**ljz
     * token键名
     */
    public static final String TOKEN_KEY = "Jwt_token";
    public static final String ADMIN_KEY = "Jwt_admin";

    /**ljz
     * 管理员账号
     */
    public static final String ADMIN_ACCOUNT = "root";
    public static final String ADMIN_PASSWORD = "666666";



    public static final String DATE_PATTERN = "yyyyMMdd";
    public static final String PROJECT_FOLDER = "D:\\ssm\\pics";
    public static final String FILE_FOLDER = "uploadImage";
    public static final String FILE_CATEGORY1 = "category1/";
}
