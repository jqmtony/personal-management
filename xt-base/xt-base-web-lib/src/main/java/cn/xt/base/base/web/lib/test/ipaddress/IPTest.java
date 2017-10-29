package cn.xt.base.base.web.lib.test.ipaddress;

import java.io.File;
import java.net.SocketException;

public class IPTest {
    /**
     * 获取当前项目的路径
     *
     * @return
     */
    public static String getRootPath() {
        System.out.println(IPTest.class.getClassLoader());
        String classPath = IPTest.class.getClassLoader().getResource("/").getPath();
        String rootPath = "";
        // windows下
        if ("\\".equals(File.separator)) {
            rootPath = classPath.substring(1, classPath.indexOf("/WEB-INF/classes"));
            rootPath = rootPath.replace("/", "\\");
        }
        // linux下
        if ("/".equals(File.separator)) {
            rootPath = classPath.substring(0, classPath.indexOf("/WEB-INF/classes"));
            rootPath = rootPath.replace("\\", "/");
        }
        System.out.println(rootPath);
        return rootPath;
    }

    public static void main(String[] args) throws SocketException {
    }
}
