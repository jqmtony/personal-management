package cn.xt.base.aop;

import cn.xt.base.core.DataSourceType;
import cn.xt.base.core.DataSourceTypeManager;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 在dao层执行前拦截，设置默认数据源
 */
@Aspect
@Component
@Order(0)
@MapperScan(basePackages = {"cn.xt.**.dao"})
public class DefaultDataSourceInterceptor {

    @Pointcut("execution(* cn.xt..dao..*(..))")
    public void defaultDataSource() {}

    @Before("defaultDataSource()")
    public void setDefaultDataSource(JoinPoint jp){
        String cName = jp.getTarget().getClass().getCanonicalName();
        String mName = ((MethodSignature)jp.getSignature()).getMethod().getName();
        System.out.println(cName+"."+mName+"执行前被拦截...");

        DataSourceTypeManager.set(DataSourceType.MASTER);
        System.out.println("-----------------------"+DataSourceTypeManager.get());
    }
}
