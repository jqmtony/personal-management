package org.durcframework.autocode.controller;

import org.durcframework.autocode.entity.DataSourceConfig;
import org.durcframework.autocode.generator.SQLService;
import org.durcframework.autocode.generator.SQLServiceFactory;
import org.durcframework.autocode.generator.TableDefinition;
import org.durcframework.autocode.service.DataSourceConfigService;
import org.durcframework.core.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class TableController extends BaseController {

    @Autowired
    private DataSourceConfigService dataSourceConfigService;

    @RequestMapping("listTable.do")
    public
    @ResponseBody
    Object listTable(int dcId, String tableName) {

        DataSourceConfig dataSourceConfig = dataSourceConfigService.get(dcId);

        if (StringUtils.isEmpty(dataSourceConfig.getDbName())) {
            return this.error("请前往[数据源配置]填写数据库名(dbName)");
        }

        SQLService service = SQLServiceFactory.build(dataSourceConfig);

        List<TableDefinition> list = service.getTableSelector(dataSourceConfig).getSimpleTableDefinitions();
        if (!StringUtils.isEmpty(tableName)) {
            List<TableDefinition> list1 = new ArrayList<TableDefinition>();
            for (TableDefinition tb : list) {
                if (tb.getTableName().indexOf(tableName) != -1) {
                    list1.add(tb);
                }
            }
            list = list1;
        }
        Map<String, Object> map = new HashMap<String, Object>();

        map.put("rows", list);

        return map;
    }

}
