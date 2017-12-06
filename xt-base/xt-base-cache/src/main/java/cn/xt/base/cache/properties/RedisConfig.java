package cn.xt.base.cache.properties;

import java.util.ResourceBundle;

/**
 * redis的配置项
 * Created by heshu on 2016/3/6.
 */
public class RedisConfig {

    /**
     * properties的配置数据
     */
    public static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("redis");

    public static String getString(String key) {
        return RESOURCE_BUNDLE.getString(key);
    }


    public final static String HOST = getString("redis.host");
    public final static int PORT = Integer.parseInt(getString("redis.port"));
    public final static int TIMEOUT = Integer.parseInt(getString("redis.timeout"));
    public final static String PASSWORD = getString("redis.password");
    public final static int MAX_TOTAL = Integer.parseInt(getString("redis.sharded.maxtotal"));
    public final static int MAX_IDLE = Integer.parseInt(getString("redis.sharded.maxidle"));
    public final static int MAX_WAIT_MILLIS = Integer.parseInt(getString("redis.sharded.maxwaitmillis"));
    public final static boolean TEST_ON_BORROW = Boolean.parseBoolean(getString("redis.testOnBorrow"));
}
