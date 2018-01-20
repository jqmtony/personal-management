package cn.xt.pmc.management.dao;

import cn.xt.base.core.BaseDao;
import cn.xt.pmc.management.model.BlogType;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* dao
* Created by xtao on 2018-1-21.
*/
public interface BlogTypeDao extends BaseDao<BlogType> {
	
    /**
    * 批量删除
    * 
    * @param ids id数组
    */
	int batchDelete(@Param("ids") Long[] ids);

    /**
     * 获取根节点
     * @return
     */
	List<BlogType> findNodeByPid(@Param("pid") Long pid);
}