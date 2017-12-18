package cn.xt.base.model;

import java.io.File;

/**
 * create by xtao
 * create in 2017/12/17 16:39
 */
public class Constant {
    public static final int ONE_MINUTE_MS = 60 * 1000;
    public static final int ONE_HOUR_MS = 60 * ONE_MINUTE_MS;
    public static final int ONE_DAY_MS = 24 * ONE_HOUR_MS;

    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static final String USER_HOME_PATH = System.getProperty("user.home");
    public static final String JRE_HOME_PATH = System.getProperty("java.home");

    public static final String LINE_SEPARATOR = System.getProperty("line.separator");
    public static final String PATH_SEPARATOR = File.separator;
}
