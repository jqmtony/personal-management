package cn.xt.pmc.management.service;

import cn.xt.base.pageable.Pager;
import cn.xt.base.service.BaseService;
import cn.xt.pmc.management.model.Blog;
import cn.xt.pmc.management.model.BlogVo;

import java.io.UnsupportedEncodingException;
import java.util.List;

public interface BlogService extends BaseService<Blog>{
    List<Blog> findByUserId(Long createBy) throws UnsupportedEncodingException;

    List<Blog> findAll() throws UnsupportedEncodingException;

    Pager<Blog> findConvertPage(BlogVo blogVo) throws UnsupportedEncodingException;
}
