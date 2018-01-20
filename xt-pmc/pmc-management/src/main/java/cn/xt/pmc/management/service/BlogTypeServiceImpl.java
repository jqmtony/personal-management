package cn.xt.pmc.management.service;

import cn.xt.base.core.BaseDao;
import cn.xt.base.service.BaseServiceImpl;
import cn.xt.pmc.management.dao.BlogTypeDao;
import cn.xt.pmc.management.model.BlogType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
* 的服务层实现类
* Created by xtao on 2018-1-21.
*/
@Service
public class BlogTypeServiceImpl extends BaseServiceImpl<BlogType> implements BlogTypeService {
    @Resource
    private BlogTypeDao blogTypeDao;

    @Override
    protected BaseDao<BlogType> getDao() {
        return blogTypeDao;
    }
    
    @Transactional
    @Override
    public int batchDelete(Long[] ids){
    	return blogTypeDao.batchDelete(ids);
    }

    @Override
    public List<BlogType> findBlogTypeLvl() {
        List<BlogType> rootNodes = blogTypeDao.findNodeByPid(0L);
        rootNodes.forEach(node ->{
            List<BlogType> secondNodes = blogTypeDao.findNodeByPid(node.getId());
            node.setChildren(secondNodes);
        });
        return rootNodes;
    }
}
