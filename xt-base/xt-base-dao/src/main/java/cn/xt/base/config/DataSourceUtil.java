package cn.xt.base.config;

import cn.xt.base.core.CustomDataSource;
import cn.xt.base.core.DataSourceType;
import org.reflections.Reflections;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 数据源工具类，主要是用于搜集系统中的所有数据源
 * Created by heshun on 2017/1/13.
 */
public class DataSourceUtil {

    private static Map<Object, Object> targetDataSources;

    public static Map<Object, Object> getDataSources() {
        if (targetDataSources == null) {
            targetDataSources = new HashMap();

            Reflections reflections = new Reflections("cn.xt.*");
//
            Set<Class<? extends CustomDataSource>> subTypes = reflections.getSubTypesOf(CustomDataSource.class);
            for (Class<? extends CustomDataSource> subType : subTypes) {
                try {
                    CustomDataSource o = (CustomDataSource) subType.newInstance();
                    DataSource dataSource = o.getDataSource();
                    DataSourceType oKey = o.getKey();
                    targetDataSources.put(oKey, dataSource);
                } catch (InstantiationException e) {
                    e.printStackTrace();
                    
                    throw new RuntimeException("get datasource fail", e);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    throw new RuntimeException("get datasource fail", e);
                }
            }
        }
        return targetDataSources;
    }

}
