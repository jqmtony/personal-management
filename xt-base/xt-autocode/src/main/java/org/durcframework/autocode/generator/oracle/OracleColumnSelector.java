package org.durcframework.autocode.generator.oracle;

import org.durcframework.autocode.generator.ColumnDefinition;
import org.durcframework.autocode.generator.ColumnSelector;
import org.durcframework.autocode.generator.DataBaseConfig;

import java.util.Map;
import java.util.Set;

/**
 * Created by likun on 2016/5/12 0012.
 */
public class OracleColumnSelector extends ColumnSelector {

    public OracleColumnSelector(DataBaseConfig dataBaseConfig) {
        super(dataBaseConfig);
    }


    @Override
    protected String getColumnInfoSQL(String tableName) {
        String TABKE_DETAIL_SQL = "select " +
                " t.COLUMN_NAME as column_name," +
                " t.DATA_TYPE as type," +
                " t.IDENTITY_COLUMN as is_identity," +
                " c.COMMENTS as comments," + " decode(t.COLUMN_NAME,(select  COLUMN_NAME from user_constraints con,user_cons_columns col where con.constraint_name=col.constraint_name  and col.table_name='" + tableName + "'),1,0) as is_pk" +
                "  from user_tab_columns t join USER_COL_COMMENTS c on t.COLUMN_NAME=c.COLUMN_NAME " +
                "  where t.table_name =  ";
        return TABKE_DETAIL_SQL+"'"+tableName+"' and c.table_name='"+tableName+"'";
    }

    @Override
    protected ColumnDefinition buildColumnDefinition(Map<String, Object> rowMap) {
        Set<String> columnSet = rowMap.keySet();

        for (String columnInfo : columnSet) {
            rowMap.put(columnInfo.toUpperCase(), rowMap.get(columnInfo));
        }

        ColumnDefinition columnDefinition = new ColumnDefinition();

        //columnDefinition.setColumnName((String)rowMap.get("COLUMN_NAME"));
        columnDefinition.setColumnName((String)rowMap.get("COLUMN_NAME"));
        columnDefinition.setIsIdentity(rowMap.get("IS_IDENTITY").equals("YES")?true:false);
        boolean isPk = rowMap.get("IS_PK").toString().equals("1");
        columnDefinition.setIsPk(isPk);
        columnDefinition.setType((String)rowMap.get("TYPE"));

        columnDefinition.setComment((String)rowMap.get("COMMENTS"));

        return columnDefinition;
    }


}
