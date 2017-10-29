package cn.xt.base.datasource;

import cn.xt.base.config.DataSourceConfig;
import cn.xt.base.config.DbConfig;
import cn.xt.base.core.CustomDataSource;
import cn.xt.base.core.DataSourceType;
import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

/**
 * Created by heshun on 2017/1/13.
 */
@Component
public class SlaveDataSource implements CustomDataSource {

    DruidDataSource dataSource;

    @Override
    public DataSource getDataSource() {
        if (dataSource == null) {
            dataSource = new DruidDataSource();
            dataSource.setDriverClassName(DbConfig.slaveDriver);
            dataSource.setUrl(DbConfig.slaveUrl);
            dataSource.setUsername(DbConfig.slaveUsername);
            dataSource.setPassword(DbConfig.slavePassword);

            dataSource.setTestWhileIdle(true);
            dataSource.setTestOnBorrow(false);
            dataSource.setTestOnReturn(false);
            dataSource.setInitialSize(10);
            dataSource.setPoolPreparedStatements(true);
            dataSource.setMaxPoolPreparedStatementPerConnectionSize(20);
            dataSource.setRemoveAbandonedTimeout(DbConfig.druidRemoveAbandonedTimeout);
            dataSource.setMinIdle(1);
            dataSource.setMaxActive(DbConfig.druidMaxActive);
            dataSource.getProxyFilters().add(DataSourceConfig.Slf4jLogFilter());
        }
        return dataSource;
    }

    @Override
    public DataSourceType getKey() {
        return DataSourceType.SLAVE;
    }
}
