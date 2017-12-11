package cn.xt.base.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.impl.client.CloseableHttpClient;

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

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    static class Test implements  Runnable {

        private Connection conn;

        private String ip;
        private Long id;

        public Test(Long id,String ip){
            this.ip = ip;
            this.id = id;

            String url = "jdbc:mysql://47.93.253.163:3306/qwkxq?useUnicode=true&characterEncoding=UTF-8";
            String user = "root";
            String password = "123456";
            Connection conn = null;
            try {
                conn = DriverManager.getConnection(url, user, password);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            this.conn = conn;
        }

        @Override
        public void run() {
            try {
                System.out.println(ip+" started");
                PreparedStatement ps = conn.prepareStatement("UPDATE remoteaddr SET isp = ?,homeloc=? WHERE id = ?");
                String isp = IpInfo.getIpIsp(ip);
                String homeloc = IpInfo.getIpHomeLocation(ip);
                System.out.println(homeloc+","+isp);
                ps.setString(1, isp);
                ps.setString(2, homeloc);
                ps.setLong(3, id);
                ps.executeUpdate();
                ps.close();
            }catch (SQLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {

            }
        }
    }

    public static void main(String[] args) throws IOException, SQLException {
        /*String mac = MacAddress.getMacAddress("106.11.227.73");
        System.out.println(mac);*/

        /*Map<String,String> map = new HashMap<>();

        String ipArr = "125.69.102.76,140.205.201.33,111.206.36.134,108.59.8.70,140.205.201.36,42.156.251.208,106.11.226.112,140.205.225.189,220.181.108.157,140.205.201.38,140.205.201.39,140.205.225.202,62.210.101.101,159.203.94.245,203.208.60.168,220.181.108.154,220.181.108.173,220.181.108.143,123.125.71.58,140.205.225.186,123.125.67.220,88.198.16.12,112.117.17.34,171.120.24.11,113.128.104.244,150.255.35.166,139.227.100.53,121.57.15.178,140.205.225.194,220.181.108.111,140.205.225.196,220.181.108.119,220.181.108.114,69.30.198.202,106.11.226.97,140.205.225.185,72.14.199.132,72.14.199.137,14.116.142.125,222.209.152.239,139.162.119.197,27.129.226.221,140.205.201.35,140.205.201.6,220.181.108.110,123.125.71.69,106.11.242.84,123.125.71.80,42.156.251.205,220.181.108.121,199.58.86.209,140.205.225.199,175.9.53.109,120.204.17.46,120.204.17.68,120.204.17.70,140.205.225.206,140.205.201.43,220.181.108.120,123.191.144.0,125.76.60.163,182.101.55.35,182.101.63.244,223.166.98.75,117.14.158.233,14.215.176.18,123.125.67.152,123.125.67.166,122.152.202.";
        StringTokenizer token = new StringTokenizer(ipArr,",");
        while(token.hasMoreTokens()){
            String ip = token.nextToken();
            map.put(ip+"_1",IpInfo.getIpIsp(ip));
            map.put(ip+"_2",IpInfo.getIpHomeLocation(ip));
        }*/


        String url = "jdbc:mysql://47.93.253.163:3306/qwkxq";
        String user = "root";
        String password = "123456";
        Connection conn = DriverManager.getConnection(url, user, password);
        PreparedStatement ps = conn.prepareStatement("SELECT id,ip FROM remoteaddr");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Long id = rs.getLong(1);
            String ip = rs.getString(2);
            new Thread(new Test(id,ip)).start();
        }
        conn.close();
        rs.close();
        ps.close();

//        System.out.println(IpInfo.getIpIsp("123.191.132.54"));
//        System.out.println(IpInfo.getIpHomeLocation("123.191.132.54"));
    }

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
            if(data!=null){
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
            if(result.get("msg")!=null && result.get("msg").toString().trim().equals("ok")){
                Map<String,String> data = (Map<String, String>) result.get("data");
                if(data!=null){
                    String desc = data.get("desc");
                    String desc1 = data.get("desc1");
                    if(StringUtils.isEmpty(desc)){
                        desc = desc1;
                    }else{
                        desc = desc+"_"+(StringUtils.isEmpty(desc1)?"":desc1);
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
