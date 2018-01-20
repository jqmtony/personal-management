package org.durcframework.autocode.generator.oracle;

import org.durcframework.autocode.generator.ColumnSelector;
import org.durcframework.autocode.generator.DataBaseConfig;
import org.durcframework.autocode.generator.TableDefinition;
import org.durcframework.autocode.generator.TableSelector;

import java.util.Map;

/**
 * Created by likun on 2016/5/12 0012.
 */
public class OracleTableSelector extends TableSelector {

    public OracleTableSelector(ColumnSelector columnSelector,
                              DataBaseConfig dataBaseConfig) {
        super(columnSelector, dataBaseConfig);
    }

    @Override
    protected String getShowTablesSQL(String dbName) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ")
                .append("t.TABLE_NAME AS table_name,")
                .append("c.COMMENTS as comments ")
                .append(" FROM USER_TABLES t join user_tab_comments c on t.TABLE_NAME=c.TABLE_NAME ");
        if(this.getSchTableNames() != null && this.getSchTableNames().size() > 0) {
            sb.append("WHERE t.TABLE_NAME in(");
            int i=0;
            for (String table : this.getSchTableNames()) {
                if(i==0){
                    sb.append("'").append(table).append("'");
                }else{
                    sb.append(",'").append(table).append("'");
                }
                i++;
            }
            sb.append(")");
        }
        return sb.toString();
    }

    @Override
    protected TableDefinition buildTableDefinition(Map<String, Object> tableMap) {
        TableDefinition tableDefinition = new TableDefinition();
        tableDefinition.setTableName((String)tableMap.get("TABLE_NAME"));
        tableDefinition.setComment((String)tableMap.get("COMMENTS"));
        return tableDefinition;
    }
}
