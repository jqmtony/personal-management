package cn.xt.pmc.management.service;

import cn.xt.base.service.BaseService;
import cn.xt.pmc.management.model.BlogType;

import java.util.List;

/**
* 的服务层接口
* Created by xtao on 2018-1-21.
*/
public interface BlogTypeService extends BaseService<BlogType> {

	/**
    * 批量删除
    * 
    * @param ids id数组
    */
	int batchDelete(Long[] ids);

	/**
	 * 获取博客类型2级节点
	 * @return
	 */
	List<BlogType> findBlogTypeLvl();
}