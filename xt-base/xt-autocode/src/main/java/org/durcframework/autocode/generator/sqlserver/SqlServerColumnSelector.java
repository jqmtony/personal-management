package org.durcframework.autocode.generator.sqlserver;

import java.util.Map;
import java.util.Set;

import org.durcframework.autocode.generator.ColumnDefinition;
import org.durcframework.autocode.generator.ColumnSelector;
import org.durcframework.autocode.generator.DataBaseConfig;

public class SqlServerColumnSelector extends ColumnSelector {
	
	private static String TABKE_DETAIL_SQL = "SELECT" +
            "	 col.name AS column_name" +
            "	, bt.name AS type" +
            "	, col.is_identity" +
            "	, ext.value AS comment" +
            "	,(" +
            "		SELECT COUNT(1) FROM sys.indexes IDX " +
            "		INNER JOIN sys.index_columns IDXC " +
            "		ON IDX.[object_id]=IDXC.[object_id] " +
            "		AND IDX.index_id=IDXC.index_id " +
            "		LEFT JOIN sys.key_constraints KC " +
            "		ON IDX.[object_id]=KC.[parent_object_id] " +
            "		AND IDX.index_id=KC.unique_index_id " +
            "		INNER JOIN sys.objects O " +
            "		ON O.[object_id]=IDX.[object_id] " +
            "		WHERE O.[object_id]=col.[object_id] " +
            "		AND O.type='U' " +
            "		AND O.is_ms_shipped=0 " +
            "		AND IDX.is_primary_key=1 " +
            "		AND IDXC.Column_id=col.column_id " +
            "	) AS is_pk " +
            "FROM sys.columns col " +
            "LEFT OUTER JOIN sys.types bt on bt.user_type_id = col.system_type_id " +
            "LEFT JOIN sys.extended_properties ext ON ext.major_id = col.object_id AND ext.minor_id = col.column_id " +
            "WHERE col.object_id = object_id('%s') " +
            "ORDER BY col.column_id";

	

	public SqlServerColumnSelector(DataBaseConfig dataBaseConfig) {
		super(dataBaseConfig);
	}

	/*	SELECT
		 col.name AS column_name
		, bt.name AS type
		, col.is_identity
		, ext.value as comment
		,(
			SELECT COUNT(1) FROM sys.indexes IDX 
			INNER JOIN sys.index_columns IDXC 
			ON IDX.[object_id]=IDXC.[object_id] 
			AND IDX.index_id=IDXC.index_id 
			LEFT JOIN sys.key_constraints KC 
			ON IDX.[object_id]=KC.[parent_object_id] 
			AND IDX.index_id=KC.unique_index_id 
			INNER JOIN sys.objects O 
			ON O.[object_id]=IDX.[object_id] 
			WHERE O.[object_id]=col.[object_id] 
			AND O.type='U' 
			AND O.is_ms_shipped=0 
			AND IDX.is_primary_key=1 
			AND IDXC.Column_id=col.column_id 
		) AS is_pk 
	FROM sys.columns col 
	LEFT OUTER JOIN sys.types bt on bt.user_type_id = col.system_type_id 
	LEFT JOIN sys.extended_properties ext ON ext.major_id = col.object_id AND ext.minor_id = col.column_id
	WHERE col.object_id = object_id('front.bar') 
	ORDER BY col.column_id;
	*/
	@Override
	protected String getColumnInfoSQL(String tableName) {
		return String.format(TABKE_DETAIL_SQL, tableName);
	}


	/*
	 * rowMap:
	 * {COLUMN_NAME=barId, IS_IDENTITY=true, COMMENT=网吧ID, IS_PK=1, TYPE=int}
	 */
	@Override
	protected ColumnDefinition buildColumnDefinition(Map<String, Object> rowMap) {
		Set<String> columnSet = rowMap.keySet();
		
		for (String columnInfo : columnSet) {
			rowMap.put(columnInfo.toUpperCase(), rowMap.get(columnInfo));
		}
		
		ColumnDefinition columnDefinition = new ColumnDefinition();
		
		columnDefinition.setColumnName((String)rowMap.get("COLUMN_NAME"));
		columnDefinition.setIsIdentity((Boolean)rowMap.get("IS_IDENTITY"));
		boolean isPk = (Integer)rowMap.get("IS_PK") == 1;
		columnDefinition.setIsPk(isPk);
		columnDefinition.setType((String)rowMap.get("TYPE"));
		
		columnDefinition.setComment((String)rowMap.get("COMMENT"));
		
		return columnDefinition;
	}

}
