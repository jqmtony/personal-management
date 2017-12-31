package cn.xt.base.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import sun.net.util.IPAddressUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IpUtil {

    private final static String IP_PATTERN_STR = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."
            + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
            + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
            + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";
    private final static String IP_PLACEHOLDER = "{ip}";
    //归属地接口
    private final static String HOME_LOCAL_SINA_URL = "http://int.dpool.sina.com.cn/iplookup/iplookup.php?format=json&ip={ip}";
    //运营商接口
    private final static String ISP_SUDOPS_URL = "https://www.sudops.com/ipq/?format=json&token=9a3061dc225dedc7d87e9ca02997b8de&ip={ip}";

    public static Long ipToLong(String ipString) {
        if (!isIpv4(ipString)) {
            return null;
        }
        Long result = new Long(0);
        java.util.StringTokenizer token = new java.util.StringTokenizer(ipString, ".");
        result += Long.parseLong(token.nextToken()) << 24;
        result += Long.parseLong(token.nextToken()) << 16;
        result += Long.parseLong(token.nextToken()) << 8;
        result += Long.parseLong(token.nextToken());
        return result;
    }

    public static String longToIp(long i) {
        return ((i >> 24) & 0xFF) + "." +
                ((i >> 16) & 0xFF) + "." +
                ((i >> 8) & 0xFF) + "." +
                (i & 0xFF);
    }

    /**
     * 是否是公网ip(只支持IPV4 的 IP)
     *
     * @param ip
     * @return
     */
    public static boolean isInternalIp(String ip) {
        byte[] addr = IPAddressUtil.textToNumericFormatV4(ip);

        final byte b0 = addr[0];
        final byte b1 = addr[1];
        //10.x.x.x/8
        final byte SECTION_1 = 0x0A;
        //172.16.x.x/12
        final byte SECTION_2 = (byte) 0xAC;
        final byte SECTION_3 = (byte) 0x10;
        final byte SECTION_4 = (byte) 0x1F;
        //192.168.x.x/16
        final byte SECTION_5 = (byte) 0xC0;
        final byte SECTION_6 = (byte) 0xA8;
        switch (b0) {
            case SECTION_1:
                return true;
            case SECTION_2:
                if (b1 >= SECTION_3 && b1 <= SECTION_4) {
                    return true;
                }
            case SECTION_5:
                switch (b1) {
                    case SECTION_6:
                        return true;
                }
            default:
                return false;
        }
    }

    public static boolean isIpv4(String ipStr) {
        if (StringUtils.isNotEmpty(ipStr)) {
            Pattern pattern = Pattern.compile(IP_PATTERN_STR);
            Matcher matcher = pattern.matcher(ipStr);
            return matcher.matches();
        }
        return false;
    }

    public static String callCmd(String[] cmd) {
        String result = "";
        String line = "";
        try {
            Process proc = Runtime.getRuntime().exec(cmd);
            InputStreamReader is = new InputStreamReader(proc.getInputStream());
            BufferedReader br = new BufferedReader(is);
            while ((line = br.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * @param cmd     第一个命令
     * @param another 第二个命令
     * @return 第二个命令的执行结果
     */

    public static String callCmd(String[] cmd, String[] another) {
        String result = "";
        String line = "";
        try {
            Runtime rt = Runtime.getRuntime();
            Process proc = rt.exec(cmd);
            proc.waitFor(); // 已经执行完第一个命令，准备执行第二个命令
            proc = rt.exec(another);
            InputStreamReader is = new InputStreamReader(proc.getInputStream());
            BufferedReader br = new BufferedReader(is);
            while ((line = br.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static class IpInfo {
        /**
         * 获取Ip归属地
         *
         * @param ip
         * @return
         */
        public static String getIpHomeLocation(String ip) throws IOException {
            CloseableHttpClient httpClient = HttpClientUtil.defaults();
            String iface = HOME_LOCAL_SINA_URL.replace(IP_PLACEHOLDER, ip);
            String json = HttpClientUtil.get(httpClient, iface, null);
            Map<String, String> data = JsonUtils.fromJson(json, Map.class);
            if (data != null) {
                return data.get("country") + data.get("province") + data.get("city");
            }
            return null;
        }

        /**
         * 获取Ip运营商
         *
         * @param ip
         * @return
         */
        public static String getIpIsp(String ip) throws IOException {
            CloseableHttpClient httpClient = HttpClientUtil.defaults();
            String iface = ISP_SUDOPS_URL.replace(IP_PLACEHOLDER, ip);
            String json = HttpClientUtil.get(httpClient, iface, null);
            Map<String, Object> result = JsonUtils.fromJson(json, Map.class);
            if (result.get("msg") != null && result.get("msg").toString().trim().equals("ok")) {
                Map<String, String> data = (Map<String, String>) result.get("data");
                if (data != null) {
                    String desc = data.get("desc");
                    String desc1 = data.get("desc1");
                    if (StringUtils.isEmpty(desc)) {
                        desc = desc1;
                    } else {
                        desc = desc + "_" + (StringUtils.isEmpty(desc1) ? "" : desc1);
                    }
                    return desc;
                }
            }
            return null;
        }
    }

    public static class MacAddress {
        /**
         * @param ip           目标ip,一般在局域网内
         * @param sourceString 命令处理的结果字符串
         * @param macSeparator mac分隔符号
         * @return mac地址，用上面的分隔符号表示
         */

        public static String filterMacAddress(final String ip, final String sourceString, final String macSeparator) {
            String result = "";
            String regExp = "((([0-9,A-F,a-f]{1,2}" + macSeparator + "){1,5})[0-9,A-F,a-f]{1,2})";
            Pattern pattern = Pattern.compile(regExp);
            Matcher matcher = pattern.matcher(sourceString);
            while (matcher.find()) {
                result = matcher.group(1);
                if (sourceString.indexOf(ip) <= sourceString.lastIndexOf(matcher.group(1))) {
                    break; // 如果有多个IP,只匹配本IP对应的Mac.
                }
            }
            return result;
        }

        /**
         * @param ip 目标ip
         * @return Mac Address
         */

        public static String getMacInWindows(final String ip) {
            String result = "";
            String[] cmd = {"cmd", "/c", "ping " + ip};
            String[] another = {"cmd", "/c", "arp -a"};
            String cmdResult = callCmd(cmd, another);
            result = filterMacAddress(ip, cmdResult, "-");
            return result;
        }

        /**
         * @param ip 目标ip
         * @return Mac Address
         */
        public static String getMacInLinux(final String ip) {
            String result = "";
            String[] cmd = {"/bin/sh", "-c", "ping " + ip + " -c 2 && arp -a"};
            String cmdResult = callCmd(cmd);
            result = filterMacAddress(ip, cmdResult, ":");
            return result;
        }

        /**
         * 获取MAC地址
         *
         * @return 返回MAC地址
         */
        public static String getMacAddress(String ip) {
            String macAddress = "";
            macAddress = getMacInWindows(ip).trim();
            if (macAddress == null || "".equals(macAddress)) {
                macAddress = getMacInLinux(ip).trim();
            }
            return macAddress;
        }
    }
}
