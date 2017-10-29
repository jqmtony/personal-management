package cn.xt.base.core;

import javax.sql.DataSource;

/**
 * 自定义数据源的接口
 * Created by heshun on 2017/1/13.
 */
public interface CustomDataSource {

    /**
     * 获取数据源
     *
     * @return
     */
    DataSource getDataSource();

    /**
     * 获取数据源路由的key
     *
     * @return
     */
    DataSourceType getKey();
}
