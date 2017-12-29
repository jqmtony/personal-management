package cn.xt.pmc.management.service;

import cn.xt.base.pageable.PageVo;
import cn.xt.base.pageable.Pager;
import cn.xt.base.service.BaseService;
import cn.xt.pmc.management.exceptions.BlogNoPermissionException;
import cn.xt.pmc.management.exceptions.BlogRepeatException;
import cn.xt.pmc.management.model.Blog;
import cn.xt.pmc.management.model.BlogVo;

import java.io.UnsupportedEncodingException;
import java.util.List;

public interface BlogService extends BaseService<Blog>{
    List<Blog> findByUserId(Long createBy) throws UnsupportedEncodingException;

    List<Blog> findAll() throws UnsupportedEncodingException;

    Pager<Blog> findConvertPage(BlogVo blogVo) throws UnsupportedEncodingException;

    /**
     * 查找重复博客数量
     * @param title
     * @param createBy
     * @return
     */
    Long findRepeatBlogSize(String title,Long createBy);

    int insertEntity(Blog entity) throws BlogRepeatException;

    int updateEntity(Blog entity) throws BlogNoPermissionException;
}
