package cn.xt.pmc.management.service;

import cn.xt.base.core.BaseDao;
import cn.xt.base.service.BaseServiceImpl;
import cn.xt.pmc.management.dao.BlogDao;
import cn.xt.pmc.management.model.Blog;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * create by xtao
 * create in 2017/11/19 20:00
 */
@Service
public class BlogServiceImpl extends BaseServiceImpl<Blog> implements BlogService{
    @Resource
    private BlogDao blogDao;

    @Override
    protected BaseDao<Blog> getDao() {
        return blogDao;
    }
}
