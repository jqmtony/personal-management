package cn.xt.base.lucene.util;

import cn.xt.base.util.StringUtil;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileUtil {
    public static String getContent(InputStream in) throws IOException {
        try {
            StringBuffer buffer = new StringBuffer();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line = null;
            while(null!=(line=reader.readLine())){
                buffer.append(line+"\r\n");
            }
            return buffer.toString();
        } finally {
            if(in!=null)in.close();
        }
    }

    public static void main(String[] args) throws IOException {
        InputStream in = FileUtil.class.getClassLoader().getResourceAsStream("1.txt");
        String content = getContent(in);
        System.out.println(content);
       /* String str = ",.! my english is not good，。！";
        String result = StringUtil.replaceByRegular("[a-z]+", str, new StringUtil.RegularReplaceStrategy() {
            @Override
            public String result(String match) {
                String s = match.substring(0,1).toUpperCase()+match.substring(1);
                return s;
            }
        });
        System.out.println(result);*/
    }
}
