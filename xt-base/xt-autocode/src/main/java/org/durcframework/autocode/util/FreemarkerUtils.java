package org.durcframework.autocode.util;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.io.StringWriter;

/**
 * Freemarker操作类
 *
 * @author fengwx
 */
public class FreemarkerUtils {

    /**
     * 根据表达式模版生成内容。如果解析出错会返回表达式的原字符串
     *
     * @param data 参数
     * @param expr 表达式
     * @return string
     */
    public static String parse(Object data, String expr) {
        try {
            StringTemplateLoader stringLoader = new StringTemplateLoader();
            stringLoader.putTemplate("tpl", expr);

            Configuration cfg = new Configuration();
            cfg.setTemplateLoader(stringLoader);
            cfg.setClassicCompatible(true); // 忽略找不到的变量
            Template template = cfg.getTemplate("tpl");

            StringWriter writer = new StringWriter();
            template.process(data, writer);
            writer.flush();
            return writer.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
			e.printStackTrace();
        }
        return expr;
    }

}
