package cn.xt.base.pageable;

import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.print.Pageable;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Intercepts(
    {
        @Signature(
            //方法调用者类型，这里指的是Executor或者其子类(mybatis中的增删改查，事务提交等都由它完成)
            type = Executor.class,
            //方法操作方式，这里只拦截”查询“操作
            method = "query",
            //方法参数类型，这里指定拦截以下类型
            args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}
        )
    }
)
public class MybatisPageableInterceptor implements Interceptor {

    private static Logger logger = LoggerFactory.getLogger(MybatisPageableInterceptor.class);

    private static final int ARGS_MAPPED_STATEMENT_INDEX = 0;
    private static final int ARGS_PARAMETER_INDEX = 1;
    private static final int ARGS_ROWBOUNDS_INDEX = 2;

    //用于缓存执行的方法是不是返回分页对象Pager
    private static final Map<String,Boolean> PAGER_METHOD_MAP = new HashMap<String,Boolean>();

    //方言，定义不同数据库差异sql语句，比如分页
    private Dialect dialect;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object[] args = invocation.getArgs();

        final MappedStatement ms = (MappedStatement) args[ARGS_MAPPED_STATEMENT_INDEX];
        final Object parameter = args[ARGS_PARAMETER_INDEX];

        boolean isPagerMethod = cachePagerMethod(ms);
        if(isPagerMethod){

            //分页请求对象
            PageVo pageReq = findPageableObject(parameter);

            final BoundSql boundSql = ms.getBoundSql(parameter);

            // 删除尾部的 ';'
            String sql = boundSql.getSql().trim().replaceAll(";$", "");

            // 1. 搞定总记录数（如果需要的话）
            int total = 0;
            if (pageReq.getCountable()) {
                total = this.queryTotal(sql, ms, boundSql);
            }

            // 2. 搞定limit 查询
            // 2.1 获取分页SQL，并完成参数准备
            String limitSql = dialect.getLimitString(sql, pageReq.getDboff(), pageReq.getRow());

            args[ARGS_ROWBOUNDS_INDEX] = new RowBounds(RowBounds.NO_ROW_OFFSET, RowBounds.NO_ROW_LIMIT);
            args[ARGS_MAPPED_STATEMENT_INDEX] = copyFromNewSql(ms, boundSql, limitSql);

            // 2.2 继续执行剩余步骤，获取查询结果
            Object ret = invocation.proceed();

            // 3. 组成分页对象
            Pager<?> pi = new Pager<>(pageReq.getPage(), pageReq.getRow(), total, (List<Object>) ret);

            // 4. MyBatis 需要返回一个List对象，这里只是满足MyBatis而作的临时包装
            List<Pager<?>> tmp = new ArrayList(1);
            tmp.add(pi);
            return tmp;
        }
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
//		return Plugin.wrap(target, this);
        if (Executor.class.isAssignableFrom(target.getClass())) {
            return Plugin.wrap(target, this);
        }

        return target;
    }

    @Override
    public void setProperties(Properties p) {
        String dialectClass = p.getProperty("dialectClass");
        try {
            this.dialect = (Dialect) Class.forName(dialectClass).newInstance();
        } catch (Exception e) {
            throw new RuntimeException("cannot create dialect instance by dialectClass:" + dialectClass, e);
        }

    }

    /**
     * 缓存分页目标方法
     * @param ms
     * @return
     */
    public boolean cachePagerMethod(MappedStatement ms){
        String statementId = ms.getId();
        Boolean isPagerMethod = PAGER_METHOD_MAP.get(statementId);
        if(isPagerMethod==null){
            int last = statementId.lastIndexOf(".");
            String daoClassName = statementId.substring(0, last);
            String daoMethodName = statementId.substring(last + 1, statementId.length());
            try {
                Class<?> daoClass = Class.forName(daoClassName);
                Method[] daoMethods = daoClass.getMethods();

                //找到目标方法
                Method target = null;
                for(Method m : daoMethods){
                    if(m.getName().equals(daoMethodName)){
                        target = m ;
                    }
                }
                //判断目标方法是否返回Pager
                if(target!=null){
                    Class<?> returnType = target.getReturnType();
                    if(Pager.class.isAssignableFrom(returnType)){
                        PAGER_METHOD_MAP.put(statementId,true);
                        isPagerMethod = true;
                    }else{
                        PAGER_METHOD_MAP.put(statementId,false);
                    }
                }
            } catch (ClassNotFoundException e) {
                logger.error("获取Mapper类失败",e);
            }
        }
        return isPagerMethod==null?false:isPagerMethod;
    }

    /**
     * 在方法参数中查找 分页请求对象
     *
     * @param params Mapper接口方法中的参数对象
     * @return
     */
    private PageVo findPageableObject(Object params) {

        if (params == null) {
            return new PageVo();
        }
        //如果参数是以对象方式传进来的。
        if (PageVo.class.isAssignableFrom(params.getClass())) {
            PageVo vo = (PageVo) params;
            return vo;
        } else if (params instanceof MapperMethod.ParamMap) {
            //如果是多个参数的，才进行查找参数中是否包含分页参数
            Map<String,Object> paramMap = new HashMap<String,Object>((Map<String,Object>) params);
            Object page = paramMap.get("page");
            Object row = paramMap.get("row");
            PageVo pageable = new PageVo();
            if(page!=null){
                pageable.setPage((Integer) page);
            }
            if(row!=null){
                pageable.setRow((Integer) row);
            }
            return pageable;
        }
        return new PageVo();
    }

    /**
     * 查询总记录数
     *
     * @param sql
     * @param mappedStatement
     * @param boundSql
     * @return
     * @throws SQLException
     */
    private int queryTotal(String sql, MappedStatement mappedStatement,
                           BoundSql boundSql) throws SQLException {
        Connection connection = null;
        PreparedStatement countStmt = null;
        ResultSet rs = null;
        try {
            connection = mappedStatement.getConfiguration().getEnvironment().getDataSource().getConnection();

            String countSql = this.dialect.getCountString(sql);

            countStmt = connection.prepareStatement(countSql);

            BoundSql countBoundSql = new BoundSql(
                    mappedStatement.getConfiguration(),
                    countSql,
                    boundSql.getParameterMappings(),
                    boundSql.getParameterObject()
            );

            //完成sql参数绑定
            setParameters(countStmt, mappedStatement, countBoundSql, boundSql.getParameterObject());

            rs = countStmt.executeQuery();
            int totalCount = 0;
            if (rs.next()) {
                totalCount = rs.getInt(1);
            }

            return totalCount;
        } catch (SQLException e) {
            logger.error("查询总记录数出错", e);
            throw e;
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    logger.error("exception happens when doing: ResultSet.close()", e);
                }
            }

            if (countStmt != null) {
                try {
                    countStmt.close();
                } catch (SQLException e) {
                    logger.error("exception happens when doing: PreparedStatement.close()", e);
                }
            }

            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    logger.error("exception happens when doing: Connection.close()", e);
                }
            }
        }

    }

    /**
     * 使用Mybatis的默认ParameterHandler完成参数绑定，即由mybatis完成
     *
     * ps.setXXX("",""); 这类操作
     *
     * @param ps
     * @param mappedStatement
     * @param boundSql
     * @param parameterObject
     * @throws SQLException
     */
    private void setParameters(PreparedStatement ps, MappedStatement mappedStatement, BoundSql boundSql,
                               Object parameterObject) throws SQLException {
        ParameterHandler parameterHandler = new DefaultParameterHandler(mappedStatement, parameterObject, boundSql);
        parameterHandler.setParameters(ps);
    }

    /**
     * 从原有的MappedStatement,重新生成一个新的MappedStatement
     * @param ms
     * @param boundSql
     * @param sql
     * @return
     */
    private MappedStatement copyFromNewSql(MappedStatement ms,
                                           BoundSql boundSql, String sql) {
        BoundSql newBoundSql = new BoundSql(ms.getConfiguration(), sql, boundSql.getParameterMappings(), boundSql.getParameterObject());
        for (ParameterMapping mapping : boundSql.getParameterMappings()) {
            String prop = mapping.getProperty();
            if (boundSql.hasAdditionalParameter(prop)) {
                newBoundSql.setAdditionalParameter(prop, boundSql.getAdditionalParameter(prop));
            }
        }

        SqlSource sqlSource = new SqlSource(){
            @Override
            public BoundSql getBoundSql(Object o) {
                return newBoundSql;
            }
        };
        return resetMappedStatement(ms,sqlSource);
    }

    /**
     * 根据MappedStatement对象构建一个新的MappedStatement对象
     * @param ms
     * @param newSqlSource
     * @return
     */
    private MappedStatement resetMappedStatement(MappedStatement ms, SqlSource newSqlSource) {
        MappedStatement.Builder builder = new MappedStatement.Builder(
                ms.getConfiguration(),
                ms.getId(),
                newSqlSource,
                ms.getSqlCommandType()
        );

        builder.resource(ms.getResource());
        builder.fetchSize(ms.getFetchSize());
        builder.statementType(ms.getStatementType());
        builder.keyGenerator(ms.getKeyGenerator());
        if (ms.getKeyProperties() != null && ms.getKeyProperties().length != 0) {
            StringBuffer keyProperties = new StringBuffer();
            for (String keyProperty : ms.getKeyProperties()) {
                keyProperties.append(keyProperty).append(",");
            }
            keyProperties.delete(keyProperties.length() - 1, keyProperties.length());
            builder.keyProperty(keyProperties.toString());
        }

        //setStatementTimeout()
        builder.timeout(ms.getTimeout());

        //setStatementResultMap()
        builder.parameterMap(ms.getParameterMap());

        //setStatementResultMap()
        builder.resultMaps(ms.getResultMaps());
        builder.resultSetType(ms.getResultSetType());

        //setStatementCache()
        builder.cache(ms.getCache());
        builder.flushCacheRequired(ms.isFlushCacheRequired());
        builder.useCache(ms.isUseCache());

        return builder.build();
    }
}
