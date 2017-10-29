package cn.xt.base.core;

/**
 * 数据源类型管理，在多线程环境下安全切换DataSource
 */
public class DataSourceTypeManager {
    private static final ThreadLocal<DataSourceType> local = new ThreadLocal<DataSourceType>(){
        @Override
        protected DataSourceType initialValue() {
            return DataSourceType.MASTER;
        }
    };
    public static void set(DataSourceType datasourceType){
        local.set(datasourceType);
    }
    public static DataSourceType get(){
        return local.get();
    }
}
