package cn.xt.base.cfgcenter.config;

import java.util.ResourceBundle;

public class SystemConfig {
    public static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("system");
    public static String getString(String key) {
        return RESOURCE_BUNDLE.getString(key);
    }
    public static final String domainName = getString("domainName");
    public static final String titlePrefix = getString("titlePrefix");
    public static final String defaultKeywords = getString("defaultKeywords");
    public static final String defaultDescription = getString("defaultDescription");
}
