package cn.xt.base.core;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 路由数据源的ThreadLocal
 * Created by heshun on 2017/1/10.
 */
public class ThreadLocalRoutingDataSource extends AbstractRoutingDataSource {


    @Override
    protected Object determineCurrentLookupKey() {
        DataSourceType dataSources = DataSourceTypeManager.get();
//        System.out.println("这次使用的是：" + dataSources + " 线程名：" + Thread.currentThread().getName());
        return dataSources;
    }


}
