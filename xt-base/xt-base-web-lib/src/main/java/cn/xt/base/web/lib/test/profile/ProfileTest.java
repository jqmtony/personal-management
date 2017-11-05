package cn.xt.base.web.lib.test.profile;

import java.util.ResourceBundle;

public class ProfileTest {
    private static final ResourceBundle RESOURCEBUNDLE = ResourceBundle.getBundle("my");
    public static void getString(String key) {
        String v = RESOURCEBUNDLE.getString(key);
        System.out.println(v);
    }
}
