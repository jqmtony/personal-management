package cn.xt.base.config;

import cn.xt.base.core.DataSourceType;
import cn.xt.base.core.ThreadLocalRoutingDataSource;
import com.alibaba.druid.filter.logging.Slf4jLogFilter;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;
import java.util.Map;

/**
 * mybatis数据源配置
 * Created by heshun on 2017/1/12.
 */
@Configuration //标识该类是spring配置项类
//开启注解式事务，并且允许代理类(cglib),该注解实现<tx:annotation-driven/> 的作用
@EnableTransactionManagement(proxyTargetClass = true, order = 0)
@Order(0)
public class DataSourceConfig implements TransactionManagementConfigurer {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    public static Slf4jLogFilter Slf4jLogFilter() {
        Slf4jLogFilter slf4jLogFilter = new Slf4jLogFilter();
        slf4jLogFilter.setDataSourceLogEnabled(false);
        slf4jLogFilter.setConnectionLogEnabled(false);
        slf4jLogFilter.setResultSetLogEnabled(false);
        slf4jLogFilter.setStatementLogEnabled(false);
        slf4jLogFilter.setStatementExecutableSqlLogEnable(true);
        return slf4jLogFilter;
    }


    static ThreadLocalRoutingDataSource localRoutingDataSource;


    //创建数据源
    @Bean(name = "dataSource")
    public static DataSource ThreadLocalRoutingDataSource() {
        if (localRoutingDataSource == null) {
            localRoutingDataSource = new ThreadLocalRoutingDataSource();
            Map<Object, Object> dataSources = DataSourceUtil.getDataSources();
            localRoutingDataSource.setTargetDataSources(dataSources);
            localRoutingDataSource.setDefaultTargetDataSource(dataSources.get(DataSourceType.MASTER));
            localRoutingDataSource.afterPropertiesSet();
        }

        return localRoutingDataSource;
    }

    //创建mybatis的sqlsession工厂
    @Bean(name = "sqlSessionFactory")
    public static SqlSessionFactory SqlSessionFactoryBean() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(ThreadLocalRoutingDataSource());
        sqlSessionFactoryBean.setConfigLocation(new ClassPathResource("mybatis-config.xml"));
        return sqlSessionFactoryBean.getObject();
    }

    //创建mybatis的db操作模板对象，使用它可以简化crud操作,若不需要省略
    @Bean(name = "sqlSessionTemplate")
    public static SqlSessionTemplate sqlSessionTemplate() throws Exception {
        SqlSessionTemplate sqlSessionTemplate = new SqlSessionTemplate(SqlSessionFactoryBean());
        return sqlSessionTemplate;
    }

    //事务管理器
    @Bean(name = "transactionManager")
    public static DataSourceTransactionManager DataSourceTransactionManager() {
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
        dataSourceTransactionManager.setDataSource(ThreadLocalRoutingDataSource());
        return dataSourceTransactionManager;
    }

    //事务模板,用于简化事务操作，若不需要可以省略
    /**
     * 使用示例：
     *
       @Resource
       TransactionTemplate transactionTemplate;

       public void save(Order o){
           this.transactionTemplate.execute(
               new TransactionCallback() {
                     @Override
                     public Object doInTransaction(TransactionStatus status) {
                        try{
                            db.save(o);
                        }catch(Exception e){
                            status.setRollbackOnly();//回滚
                        }
                     }
                })
            );
       }
     *
     */
    //@Bean(name = "transactionTemplate")
    public static TransactionTemplate TransactionTemplate() {
        TransactionTemplate transactionTemplate = new TransactionTemplate();
        transactionTemplate.setTransactionManager(DataSourceTransactionManager());
        return transactionTemplate;
    }

    //表示注解式事务使用哪个事务管理器,等同于
    // <tx:annotation-driven transaction-manager="transactionManager" />
    @Override
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return DataSourceTransactionManager();
    }

}
