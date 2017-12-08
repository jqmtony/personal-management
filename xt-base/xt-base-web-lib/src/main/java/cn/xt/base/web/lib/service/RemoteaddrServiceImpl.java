package cn.xt.base.web.lib.service;

import cn.xt.base.core.BaseDao;
import cn.xt.base.service.BaseServiceImpl;
import cn.xt.base.web.lib.dao.RemoteaddrDao;
import cn.xt.base.web.lib.model.Remoteaddr;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * create by xtao
 * create in 2017/11/19 20:00
 */
@Service
public class RemoteaddrServiceImpl extends BaseServiceImpl<Remoteaddr> implements RemoteaddrService {
    @Resource
    private RemoteaddrDao remoteaddrDao;

    @Override
    protected BaseDao<Remoteaddr> getDao() {
        return remoteaddrDao;
    }
}
