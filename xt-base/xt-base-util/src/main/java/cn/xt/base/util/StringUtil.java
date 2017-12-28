package cn.xt.base.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil extends org.apache.commons.lang3.StringUtils {
    public static interface RegularReplaceStrategy {
        public String result(String match);
    }

    public static String replaceByRegular(String regx, String src, RegularReplaceStrategy strategy) {
        Pattern p = Pattern.compile(regx);
        Matcher m = p.matcher(src);
        StringBuffer sb = new StringBuffer();
        while (m.find()) {
            String group = m.group();
            /**
             * appendReplacement： 把匹配正则的位置替换成指定字符串
             */
            m.appendReplacement(sb, strategy.result(group));
        }
        /**
         * appendTail： 将不匹配正则的字符串放置到stringbuffer，且位置跟原字符串一致
         */
        m.appendTail(sb);
        return sb.toString();
    }
}
