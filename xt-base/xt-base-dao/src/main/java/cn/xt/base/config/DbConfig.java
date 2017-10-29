package cn.xt.base.config;

import java.util.ResourceBundle;

/**
 * Created by lijr on 2016/10/13.
 */
public class DbConfig {

    /**
     * 读取db.properties
     **/
    public static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("db");

    public static String getString(String key) {
        return RESOURCE_BUNDLE.getString(key);
    }

    public final static String masterDriver = getString("master.driver");

    public final static String masterUrl = getString("master.url");

    public final static String masterUsername = getString("master.username");

    public final static String masterPassword = getString("master.password");

    public final static String slaveDriver = getString("slave.driver");

    public final static String slaveUrl = getString("slave.url");

    public final static String slaveUsername = getString("slave.username");

    public final static String slavePassword = getString("slave.password");

    public final static String localDriver = getString("local.driver");

    public final static String localUrl = getString("local.url");

    public final static String localUsername = getString("local.username");

    public final static String localPassword = getString("local.password");

    public final static int druidMaxActive = Integer.valueOf(getString("druid.maxActive"));

    public final static int druidMinPoolSize = Integer.valueOf(getString("druid.minPoolSize"));

    public final static int druidMaxIdle = Integer.valueOf(getString("druid.maxIdle"));

    public final static int druidRemoveAbandonedTimeout = Integer.valueOf(getString("druid.removeAbandonedTimeout"));

}
