package org.durcframework.autocode.generator;

import org.durcframework.autocode.util.SqlHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 表信息查询
 */
public abstract class ColumnSelector {

    private DataBaseConfig dataBaseConfig;

    public ColumnSelector(DataBaseConfig dataBaseConfig) {
        this.dataBaseConfig = dataBaseConfig;
    }

    /**
     * 返回查询表字段信息的SQL语句,不同的数据查询表信息不一样
     * 如mysql是DESC tableName
     *
     * @return
     */
    protected abstract String getColumnInfoSQL(String tableName);

    /**
     * 构建列信息
     *
     * @param rowMap
     * @return
     */
    protected abstract ColumnDefinition buildColumnDefinition(Map<String, Object> rowMap);

    public List<ColumnDefinition> getColumnDefinitions(String tableName) {
        List<Map<String, Object>> resultList = SqlHelper.runSql(this.getDataBaseConfig(), getColumnInfoSQL(tableName));
        if (resultList == null) {
            //如果系统关键字的表，会无法查询。
            resultList = SqlHelper.runSql(this.getDataBaseConfig(), getColumnInfoSQL("`" + tableName + "`"));
        }

        List<ColumnDefinition> columnDefinitionList = new ArrayList<ColumnDefinition>(resultList.size());
        // 构建columnDefinition
        for (Map<String, Object> rowMap : resultList) {
            columnDefinitionList.add(buildColumnDefinition(rowMap));
        }

        return columnDefinitionList;
    }

    public DataBaseConfig getDataBaseConfig() {
        return dataBaseConfig;
    }

    public void setDataBaseConfig(DataBaseConfig dataBaseConfig) {
        this.dataBaseConfig = dataBaseConfig;
    }

}