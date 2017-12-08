package cn.xt.pmc.management.dao;

import cn.xt.base.core.BaseDao;
import cn.xt.pmc.management.model.Blog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BlogDao extends BaseDao<Blog> {
    List<Blog> findByUserId(@Param("createBy") Long createBy);

    List<Blog> findAll();

    /**
     * 查找重复博客数量
     * @param title
     * @param createBy
     * @return
     */
    Long findRepeatBlogSize(@Param("title") String title,@Param("createBy") Long createBy);
}
