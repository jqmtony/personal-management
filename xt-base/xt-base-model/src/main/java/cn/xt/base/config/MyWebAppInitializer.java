package cn.xt.base.config;

import org.springframework.web.WebApplicationInitializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

/**
 * Created by heshun on 2017/1/12.
 */
public class MyWebAppInitializer implements WebApplicationInitializer {

    /**
     * Servlet容器启动时会自动运行该方法
     */
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {

        //MyAnnotationConfigWebApplicationContext c = new MyAnnotationConfigWebApplicationContext();
        //c.register(AppConfig.class);

//        c.refresh();
//        AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
//        rootContext.register(AppConfig.class);
//        rootContext.setre
//        rootContext.refresh();
    }
}
